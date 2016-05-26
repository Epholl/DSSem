package sk.epholl.dissim.sem3.gui;

import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.simulation.SimulationParameters;
import sk.epholl.dissim.sem3.util.Log;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomáš on 26.05.2016.
 */
public class MainWindow extends JFrame {
    private JTabbedPane mainTabbedPane;
    private JPanel panel1;
    private JPanel inputParametersPanel;
    private JPanel singleSimPanel;
    private JPanel multipleSimPanel;
    private JTabbedPane tabbedPane1;
    private JTable vehicleTable;
    private JLabel totalVehicleLabel;
    private JTextField countVehicle1;
    private JTextField countVehicle2;
    private JTextField countVehicle3;
    private JTextField countVehicle4;
    private JTextField countVehicle5;
    private JCheckBox secondUnloaderCheckbox;
    private JLabel totalCostLabel;
    private JComboBox sleepingAreaComboBox;
    private JTextField wakeupTimeTextField;
    private JTextArea consoleTextArea;
    private JButton startButton;
    private JSlider simSPeedSlider;
    private JLabel simTimeLabel;
    private JLabel simSpeedLabel;
    private JTabbedPane tabbedPane2;
    private JLabel materialOnALabel;
    private JLabel materialOnBLabel;
    private JLabel deliveriesALabel;
    private JLabel deliveriesBLabel;
    private JLabel deliveriesCLabel;

    private PrintStream consolePrintStream;

    private SimulationParameters simulationParameters;
    private List<SimulationParameters.Vehicle> vehicleTypes;

    private MySimulation simulation;
    private boolean simRunning = false;

    public MainWindow() {
        super("sem 3");
        simulationParameters = SimulationParameters.getDefaultParameters();
        vehicleTypes = SimulationParameters.getVanillaVehicleTypes();

        setContentPane(mainTabbedPane);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        initGeneralPanel();
        initVehiclesPanel();
        initConsolePanel();
        initSingleRunPanel();
        setVisible(true);

    }

    private void initSingleRunPanel() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!simRunning) {
                    simRunning = true;
                    startButton.setText("Pause");
                    createSingleRunSimulation();
                }
            }
        });
        simSPeedSlider.addChangeListener(e -> {
            updateSingleSimulationSpeed();
        });
    }

    private void initConsolePanel() {
        consolePrintStream = new PrintStream(new Console(consoleTextArea));
        // TODO use IPC buffer
        //System.setOut(consolePrintStream);
        //System.setErr(consolePrintStream);
        consoleTextArea.setFocusable(false);
        Log.println("Console output ready...");
    }

    private void initGeneralPanel() {
        secondUnloaderCheckbox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateGuiAndParams();
            }
        });

        sleepingAreaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGuiAndParams();
            }
        });

        wakeupTimeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGuiAndParams();
            }
        });
    }

    private void initVehiclesPanel() {

        vehicleTable.setModel(new AbstractTableModel() {
            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Capacity (t)";
                    case 1:
                        return "Speed (km/h)";
                    case 2:
                        return "Breakdown probability";
                    case 3:
                        return "Repair time (min)";
                    case 4:
                        return "Price (€)";
                    case 5:
                        return "Max amount";
                    case 6:
                        return "Set amount";
                }
                return "";
            }

            @Override
            public int getRowCount() {
                return vehicleTypes.size();
            }

            @Override
            public int getColumnCount() {
                return 6;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return vehicleTypes.get(rowIndex).capacity;
                    case 1:
                        return vehicleTypes.get(rowIndex).speed;
                    case 2:
                        return vehicleTypes.get(rowIndex).breakdownProbability;
                    case 3:
                        return vehicleTypes.get(rowIndex).repairTime;
                    case 4:
                        return vehicleTypes.get(rowIndex).price;
                    case 5:
                        long maxCount = vehicleTypes.get(rowIndex).maxCount;
                        return maxCount > 100 ? "Unlimited" : maxCount;
                }
                return null;
            }
        });

        for (JTextField field: getVehicleTextFields()) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateGuiAndParams();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateGuiAndParams();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateGuiAndParams();
                }
            });
        }
        updateGuiAndParams();
;
    }

    public long getTotalVehiclesPrice() {
        long sum = 0L;
        List<JTextField> list = getVehicleTextFields();
        for (int i = 0; i < vehicleTypes.size(); i++) {
            sum += (Long)vehicleTable.getValueAt(i, 4) * getIntFrom(list.get(i));
        }
        return sum;
    }

    public long getTotalVehiclesCount() {
        long sum = 0L;
        for (JTextField jTextField : getVehicleTextFields()) {
            sum += getIntFrom(jTextField);
        }
        return sum;
    }

    public void updateGuiAndParams() {
        showVehicleInfoLabel();
        showTotalProjectCostLabel();
        updateSingleSimulationSpeed();

        simulationParameters.availableVehicles = getVehiclesForSimulation();
        simulationParameters.unloaderCount = secondUnloaderCheckbox.isSelected()? 2 : 1;
        if (sleepingAreaComboBox.getSelectedIndex() == 0) {
            simulationParameters.parkingArea = SimulationParameters.NightParking.LOADERS;
        } else {
            simulationParameters.parkingArea = SimulationParameters.NightParking.UNLOADERS;
        }
        simulationParameters.wakeupTime = parseWakeupTime();
    }

    public void createSingleRunSimulation() {
        simulation = new MySimulation(simulationParameters);
        updateSingleSimulationSpeed();
        simulation.onRefreshUI(sim -> {
            SwingUtilities.invokeLater( () -> {
                updateSimTimeLabel();
                materialOnALabel.setText(String.format("%.2f", simulation.loaderAgent().getCurrentStorageCargo()));
                materialOnBLabel.setText(String.format("%.2f", simulation.unloaderAgent().getCurrentStorageCargo()));

            });
        });
        simulation.onSimulationDidFinish(sim -> {
            SwingUtilities.invokeLater( () -> {
                updateSimTimeLabel();
            });
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                simulation.simulateAutoFigureEndDate(1);
            }
        }).start();
    }

    private static final long[] UNITS = {2419200, 604800, 86400, 3600, 60, 1};
    private static final String[] UNIT_NAMES = {" months", " weeks", " days", " hours", " minutes", " seconds"};

    private void updateSingleSimulationSpeed() {
        double value = ((double)simSPeedSlider.getValue());
        value = value * value * value;
        if (simulation != null) {
            simulation.setSimSpeed(value, 0.1);
        }

        value *= 10;
        for (int i = 0;;i++) {
            if (value >= UNITS[i]) {
                String stringValue = String.format("%.2f", (value / UNITS[i]));
                simSpeedLabel.setText("1s = " + stringValue + UNIT_NAMES[i]);
                break;
            }
        }

    }

    private void updateSimTimeLabel() {
        if (simulation == null) {
            simTimeLabel.setText("Simulation not running");
        } else {
            if (simulation.isRunning()) {
                simTimeLabel.setText("Simulation time: " + simulation.getSimDateTime());
            } else if (simulation.isPaused()) {
                simTimeLabel.setText("Simulation paused at time: " + simulation.getSimDateTime());
            } else {
                simTimeLabel.setText("Simulation stopped at time: " + simulation.getSimDateTime());

            }
        }
    }

    private void showVehicleInfoLabel() {
        totalVehicleLabel.setText("Total vehicle price: " + getTotalVehiclesPrice() + "€, total vehicle count: " + getTotalVehiclesCount());
    }

    private void showTotalProjectCostLabel() {
        totalCostLabel.setText((getTotalVehiclesPrice() + getSecondUnloaderPrice()) + "€");
    }

    private int getIntFrom(JTextField text) {
        try {
            int i = Integer.parseInt(text.getText());
            return i;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private List<JTextField> getVehicleTextFields() {
        List<JTextField> list = new ArrayList<>();
        list.add(countVehicle1);
        list.add(countVehicle2);
        list.add(countVehicle3);
        list.add(countVehicle4);
        list.add(countVehicle5);
        return list;
    }

    private List<SimulationParameters.Vehicle> getVehiclesForSimulation() {
        List<SimulationParameters.Vehicle> returned = new ArrayList<>();

        List<JTextField> list = getVehicleTextFields();
        for (int i = 0; i < vehicleTypes.size(); i++) {
            int typeCount = getIntFrom(list.get(i));
            for (int j = 0; j < typeCount; j++) {
                returned.add(vehicleTypes.get(i).clone());
            }
        }
        return returned;
    }

    private long getSecondUnloaderPrice() {
        return secondUnloaderCheckbox.isSelected() ? 130000 : 0;
    }

    private LocalTime parseWakeupTime() {
        try {
            String text = wakeupTimeTextField.getText();
            int hours = Integer.parseInt(text.split(":")[0]);
            int minutes = Integer.parseInt(text.split(":")[1]);
            return LocalTime.of(hours, minutes);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return LocalTime.of(7, 0);
        }
    }
}

