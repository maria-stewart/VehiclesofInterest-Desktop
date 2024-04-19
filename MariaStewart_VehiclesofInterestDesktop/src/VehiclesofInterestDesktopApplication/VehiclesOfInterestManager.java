/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VehiclesofInterestDesktopApplication;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Maria Stewart 
 */
public class VehiclesOfInterestManager extends javax.swing.JFrame {

     
     
    /**
     * Creates new form LoadingScreen
     */
    public VehiclesOfInterestManager() {
        initComponents();
        populateVehiclesOfInterestManagerTable(); //Call the method to populate the table 
        updateVehiclesOfInterestTable(); 
        populateReasonsComboBox(); 
        populateMakeComboBox(); 
        populateModelComboBox(); 
        populateColorComboBox();
        resetVehicleOfInterestData(); 
        deleteSelectedVehicleOfInterest(); 
        
        
      // Add ListSelectionListener to the table
    vehiclesofinterestmanagertable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting()) {
                // Call a method to handle row selection
                handleTableSelection();
            }
        }
    });

      
    
    }
    
   private int selectedRowIndex = -1;
  
   //Populate information in Tables 
   
private void populateVehiclesOfInterestManagerTable() {
        // Step 1: Create an instance of VehiclesOfInterestController
        DefaultTableModel model = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();
        VehiclesOfInterestController controller = new VehiclesOfInterestController();

        // Step 2: Retrieve Data
        String[][] vehicleData = controller.allVehicleOfInterest();
        
        // Clear existing rows
        model.setRowCount(0);

        // Step 3: Set Up JTable
        for (String[] rowData : vehicleData) {
            model.addRow(rowData);
    }
    }
 private void updateVehiclesOfInterestTable() {
        DefaultTableModel model = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();
        
        // Clear existing rows
        model.setRowCount(0);
        
        //Add a new row to the tabel 
        populateVehiclesOfInterestManagerTable();  
        
    }
 
private void populateReasonsComboBox() {
        VehiclesOfInterestController controller = new VehiclesOfInterestController();

        //JComboBox named reasonselectionComboBox
        JComboBox<String> reasonComboBox = reasonselectionComboBox;

        // Retrieve reasons for interest from the controller
        String[][] reasonsData = controller.getAllReasonsForInterest();

        // Clear existing items
        reasonComboBox.removeAllItems();

        // Populate the JComboBox with retrieved reasons for interest
        for (String[] reasonData : reasonsData) {
            reasonComboBox.addItem(reasonData[0]); // Assuming the first column is the reason
        }
    }

private void populateMakeComboBox() {
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    
    // JComboBox named makeselectionComboBox
    JComboBox<String> makeComboBox = makeselectionComboBox;

    // Retrieve makes from the controller
    String[] allMakes = controller.allVehicleMake();

    // Clear existing items
    makeComboBox.removeAllItems();

    // Populate the JComboBox with retrieved makes
    for (String make : allMakes) {
        makeComboBox.addItem(make);
    }
}

private void populateModelComboBox() {
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    
    // JComboBox named modelselectionComboBox
    JComboBox<String> modelComboBox = modelselectionComboBox;

    // Retrieve models from the controller
    String[][] allModels = controller.allVehicleModel();

    // Clear existing items
    modelComboBox.removeAllItems();

    // Populate the JComboBox with retrieved models
    for (String[] modelData : allModels) {
        modelComboBox.addItem(modelData[0]); // Assuming the first column is the model
    }
}
 
private void populateColorComboBox() {
    VehiclesOfInterestController controller = new VehiclesOfInterestController();

    // JComboBox named colorselectionComboBox
    JComboBox<String> colorComboBox = colorselectionComboBox;

    // Retrieve colors from the controller
    String[][] allColors = controller.allVehicleModel(); // Assuming method for colors is allVehicleModel, adjust if needed

    // Clear existing items
    colorComboBox.removeAllItems();

    // Populate the JComboBox with retrieved colors
    for (String[] colorData : allColors) {
        colorComboBox.addItem(colorData[0]); // Assuming the first column is the color
    }
}

//Select infomration from table and perform add, update, delete, reset 


//Handle event when a row is selected in the JTable 
/*
When a user clicks on a row in the table
the method is triggered to capture the data selected 
additionally populates various input feilds in UI
*/
private void handleTableSelection() {
    // Get the index of the selected row in the JTable
    int selectedRow = vehiclesofinterestmanagertable.getSelectedRow();
// Check if a row is selected
    if (selectedRow != -1) {
        // Retrieve data from the selected row in the JTable
        String licensePlate = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 0);
        String reason = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 1);
        String make = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 2);
        String model = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 3);
        String vehicleYear = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 4);
        String color = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 5);
        String ownersName = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 6);
        String ownersPhone = (String) vehiclesofinterestmanagertable.getValueAt(selectedRow, 7);
 // Set the retrieved data to corresponding input fields
        licenseplateinputforviomanager.setText(licensePlate);
        reasonselectionComboBox.setSelectedItem(reason);
        makeselectionComboBox.setSelectedItem(make);
        modelselectionComboBox.setSelectedItem(model);
        vehicleyearinputforvoimanager.setText(vehicleYear);
        colorselectionComboBox.setSelectedItem(color);
        ownersnameinputforvoimanager.setText(ownersName);
        ownersphonenumberforvoimanager.setText(ownersPhone);
        
       // Update selectedRowIndex to store the index of the selected row
        selectedRowIndex = selectedRow; 
    }
}

//Update a selected row in the table 
private void updateVehicleOfInterestData() {
   if (selectedRowIndex != -1) {
        String licensePlate = licenseplateinputforviomanager.getText();
        String reason = reasonselectionComboBox.getSelectedItem().toString();
        String make = makeselectionComboBox.getSelectedItem().toString();
        String model = modelselectionComboBox.getSelectedItem().toString();
        String vehicleYear = vehicleyearinputforvoimanager.getText();
        String color = colorselectionComboBox.getSelectedItem().toString();
        String ownersName = ownersnameinputforvoimanager.getText();
        String ownersPhone = ownersphonenumberforvoimanager.getText();

        System.out.println("Updating with values:");
        System.out.println("License Plate: " + licensePlate);
        System.out.println("Reason: " + reason);
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Vehicle Year: " + vehicleYear);
        System.out.println("Color: " + color);
        System.out.println("Owner's Name: " + ownersName);
        System.out.println("Owner's Phone: " + ownersPhone);

        VehiclesOfInterestController controller = new VehiclesOfInterestController();
        controller.updatedVehicleOfInterest(licensePlate, reason, make, model, vehicleYear, color, ownersName, ownersPhone);

        // Get the existing model from the table
        DefaultTableModel vehicleTableModel = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();

        // Update the selected row in the table model
        vehicleTableModel.setValueAt(licensePlate, selectedRowIndex, 0);
        vehicleTableModel.setValueAt(reason, selectedRowIndex, 1);
        vehicleTableModel.setValueAt(make, selectedRowIndex, 2);
        vehicleTableModel.setValueAt(model, selectedRowIndex, 3);
        vehicleTableModel.setValueAt(vehicleYear, selectedRowIndex, 4);
        vehicleTableModel.setValueAt(color, selectedRowIndex, 5);
        vehicleTableModel.setValueAt(ownersName, selectedRowIndex, 6);
        vehicleTableModel.setValueAt(ownersPhone, selectedRowIndex, 7);

        // Show a popup notification for successful update
        JOptionPane.showMessageDialog(null, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear selection after update
        vehiclesofinterestmanagertable.clearSelection();

    } else {
        System.out.println("No row selected for update.");
    }
    }

//Method to reset all feilds to start over entering new data 
 private void resetVehicleOfInterestData() {
    licenseplateinputforviomanager.setText("");
    vehicleyearinputforvoimanager.setText("");
    ownersnameinputforvoimanager.setText("");
    ownersphonenumberforvoimanager.setText("");

    reasonselectionComboBox.setSelectedIndex(0);
    makeselectionComboBox.setSelectedIndex(0);
    modelselectionComboBox.setSelectedIndex(0);
    colorselectionComboBox.setSelectedIndex(0);
}

 private void addNewVehicleOfInterest() {
  String licensePlate = licenseplateinputforviomanager.getText();
    String reason = reasonselectionComboBox.getSelectedItem().toString();
    String make = makeselectionComboBox.getSelectedItem().toString();
    String model = modelselectionComboBox.getSelectedItem().toString();
    String vehicleYear = vehicleyearinputforvoimanager.getText();
    String color = colorselectionComboBox.getSelectedItem().toString();
    String ownersName = ownersnameinputforvoimanager.getText();
    String ownersPhone = ownersphonenumberforvoimanager.getText();

    // Check if the 'License Plate' field is empty
    if (licensePlate.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill out the 'License Plate' field.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Stop the method if the field is empty
    }
    
    // Call the controller method to add a new vehicle of interest
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    controller.addNewVehicleOfInterest(licensePlate, reason, make, model, vehicleYear, color, ownersName, ownersPhone);

    // Get the existing model from the table
    DefaultTableModel vehicleTableModel = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();

    // Add a new row to the table model
    vehicleTableModel.addRow(new Object[]{licensePlate, reason, make, model, vehicleYear, color, ownersName, ownersPhone});
        
    // Clear input fields after adding
    resetVehicleOfInterestData();
    
    // Show a popup notification for successful deletion
        JOptionPane.showMessageDialog(null, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
}
 
 private void deleteSelectedVehicleOfInterest() {
    // Get the selected row from the table
    int selectedRow = vehiclesofinterestmanagertable.getSelectedRow();

    // Check if a row is selected
    if (selectedRow >= 0) {
        // Get the license plate from the selected row
        String licensePlate = vehiclesofinterestmanagertable.getValueAt(selectedRow, 0).toString();

        // Call the controller method to delete the selected vehicle of interest
        VehiclesOfInterestController controller = new VehiclesOfInterestController();
        controller.deleteVehicleOfInterest(licensePlate);
        
        // Show a popup notification for successful deletion
        JOptionPane.showMessageDialog(null, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Remove the selected row from the table model
        DefaultTableModel vehicleTableModel = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();
        vehicleTableModel.removeRow(selectedRow);
    } else {
        // No row selected, show an error message or handle accordingly
        System.out.println("Please select a row to delete.");
    }
}
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        vehiclesofinterestmanagertable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        licenseplateinputforviomanager = new javax.swing.JTextField();
        vehicleyearinputforvoimanager = new javax.swing.JTextField();
        ownersnameinputforvoimanager = new javax.swing.JTextField();
        ownersphonenumberforvoimanager = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        modelselectionComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        colorselectionComboBox = new javax.swing.JComboBox<>();
        reasonselectionComboBox = new javax.swing.JComboBox<>();
        makeselectionComboBox = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        searchvoimanagertxtfeild = new javax.swing.JTextField();
        addnewvehicleofintrestdata = new javax.swing.JButton();
        resetvehicledofinterestdata = new javax.swing.JButton();
        leavevehcileofinterestmanager = new javax.swing.JButton();
        updatedvehicleofinterestdata = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        deletevehicledofinterestdata1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        helpinformationbuttonvoimanager = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel5.setIcon(new javax.swing.ImageIcon("E:\\School\\SWEN 661\\Desktop Project code\\statepolicelogoresize.png")); // NOI18N
        jLabel5.setText("jLabel1");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel6.setText("Vehicle of Interest Manager");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(359, 359, 359)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        vehiclesofinterestmanagertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "License Plate", "Reason", "Make", "Model", "Vehicle Year", "Color", "Owners Name", "Owners Phone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        vehiclesofinterestmanagertable.setShowGrid(true);
        vehiclesofinterestmanagertable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vehiclesofinterestmanagertableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(vehiclesofinterestmanagertable);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("Search Data");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1398, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 658, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addGap(0, 658, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ownersnameinputforvoimanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownersnameinputforvoimanagerActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("License Plate");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setText("Vehicle Year");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel10.setText("Owners Phone");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setText("Color");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel12.setText("Owners Name");

        modelselectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        modelselectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelselectionComboBoxActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setText("Reason");

        colorselectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        colorselectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorselectionComboBoxActionPerformed(evt);
            }
        });

        reasonselectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reasonselectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reasonselectionComboBoxActionPerformed(evt);
            }
        });

        makeselectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        makeselectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeselectionComboBoxActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel16.setText("Model");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setText("Make");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addComponent(jLabel17)))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(makeselectionComboBox, 0, 267, Short.MAX_VALUE)
                    .addComponent(licenseplateinputforviomanager)
                    .addComponent(reasonselectionComboBox, 0, 267, Short.MAX_VALUE)
                    .addComponent(modelselectionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(colorselectionComboBox, 0, 263, Short.MAX_VALUE)
                    .addComponent(vehicleyearinputforvoimanager)
                    .addComponent(ownersphonenumberforvoimanager)
                    .addComponent(ownersnameinputforvoimanager))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licenseplateinputforviomanager, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vehicleyearinputforvoimanager, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorselectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(reasonselectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ownersnameinputforvoimanager, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(makeselectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(ownersphonenumberforvoimanager, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(modelselectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addGap(31, 31, 31))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        searchvoimanagertxtfeild.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchvoimanagertxtfeildKeyReleased(evt);
            }
        });

        addnewvehicleofintrestdata.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addnewvehicleofintrestdata.setText("Add New");
        addnewvehicleofintrestdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addnewvehicleofintrestdataActionPerformed(evt);
            }
        });

        resetvehicledofinterestdata.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetvehicledofinterestdata.setText("Reset");
        resetvehicledofinterestdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetvehicledofinterestdataActionPerformed(evt);
            }
        });

        leavevehcileofinterestmanager.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        leavevehcileofinterestmanager.setText("Exit");
        leavevehcileofinterestmanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leavevehcileofinterestmanagerActionPerformed(evt);
            }
        });

        updatedvehicleofinterestdata.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updatedvehicleofinterestdata.setText("Update");
        updatedvehicleofinterestdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatedvehicleofinterestdataActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Search Data");

        deletevehicledofinterestdata1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deletevehicledofinterestdata1.setText("Delete");
        deletevehicledofinterestdata1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletevehicledofinterestdata1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchvoimanagertxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(updatedvehicleofinterestdata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addnewvehicleofintrestdata, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deletevehicledofinterestdata1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(resetvehicledofinterestdata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(leavevehcileofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchvoimanagertxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addnewvehicleofintrestdata, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletevehicledofinterestdata1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(updatedvehicleofinterestdata, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(resetvehicledofinterestdata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(leavevehcileofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setIcon(new javax.swing.ImageIcon("E:\\School\\helpicon.png")); // NOI18N
        jLabel18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel18KeyPressed(evt);
            }
        });

        helpinformationbuttonvoimanager.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        helpinformationbuttonvoimanager.setText("Help");
        helpinformationbuttonvoimanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpinformationbuttonvoimanagerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(609, 609, 609)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(helpinformationbuttonvoimanager, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(helpinformationbuttonvoimanager)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addnewvehicleofintrestdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addnewvehicleofintrestdataActionPerformed
        //Call on method to add new record 
        addNewVehicleOfInterest(); 
    }//GEN-LAST:event_addnewvehicleofintrestdataActionPerformed

    private void leavevehcileofinterestmanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leavevehcileofinterestmanagerActionPerformed
        // Exit current screen and go back to the main page 
        Main mainpage = new Main();
        mainpage.show(); //display the main page here
        
        dispose(); //close current frame and go back to the main page 
    }//GEN-LAST:event_leavevehcileofinterestmanagerActionPerformed

    private void updatedvehicleofinterestdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatedvehicleofinterestdataActionPerformed
        // TODO add your handling code here:
        updateVehicleOfInterestData();
    }//GEN-LAST:event_updatedvehicleofinterestdataActionPerformed

    private void modelselectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelselectionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modelselectionComboBoxActionPerformed

    private void colorselectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorselectionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorselectionComboBoxActionPerformed

    private void reasonselectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reasonselectionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reasonselectionComboBoxActionPerformed

    private void makeselectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeselectionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_makeselectionComboBoxActionPerformed

    private void searchvoimanagertxtfeildKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchvoimanagertxtfeildKeyReleased
        // Create object for table model to search reason of interest on in the database
        DefaultTableModel ob = (DefaultTableModel) vehiclesofinterestmanagertable.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        vehiclesofinterestmanagertable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchvoimanagertxtfeild.getText()));
    }//GEN-LAST:event_searchvoimanagertxtfeildKeyReleased

    private void vehiclesofinterestmanagertableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehiclesofinterestmanagertableMouseClicked
        // call on handle to complete action 
        handleTableSelection();
    }//GEN-LAST:event_vehiclesofinterestmanagertableMouseClicked

    private void resetvehicledofinterestdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetvehicledofinterestdataActionPerformed
       //call method to reset all feilds 
      resetVehicleOfInterestData(); 
       
    }//GEN-LAST:event_resetvehicledofinterestdataActionPerformed

    private void jLabel18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel18KeyPressed

    }//GEN-LAST:event_jLabel18KeyPressed

    private void helpinformationbuttonvoimanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpinformationbuttonvoimanagerActionPerformed
        // This will provided a pop up window telling the user what to do on the screen
        // Create a custom dialog
    JDialog helpDialog = new JDialog(this, "Help Information", true);

    // Set the help message
    String helpMessage = "To search for a specific vehicle of interest, you can search for it by tpying in the search data bar. If you want to add a record, fill out the required information then click 'Add New'."
            + "To update and delete records you need to click on the record in the table, then the desired action. You can reset the feilds if you want to start over by clicking 'Reset'. To go back to the homepage click 'Exit'. ";

    // Create a JLabel to display the help message
    JLabel helpLabel = new JLabel("<html>" + helpMessage + "</html>");
    helpLabel.setHorizontalAlignment(JLabel.CENTER);

    // Set the layout and size of the dialog
    helpDialog.setLayout(new BorderLayout());
    helpDialog.setSize(410, 200); // Set your desired width and height
    helpDialog.setLocationRelativeTo(this);

    // Add the helpLabel to the dialog
    helpDialog.add(helpLabel, BorderLayout.CENTER);

    // Display the custom dialog
    helpDialog.setVisible(true);
        
    }//GEN-LAST:event_helpinformationbuttonvoimanagerActionPerformed

    private void ownersnameinputforvoimanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownersnameinputforvoimanagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ownersnameinputforvoimanagerActionPerformed

    private void deletevehicledofinterestdata1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletevehicledofinterestdata1ActionPerformed
        // TODO add your handling code here:
         deleteSelectedVehicleOfInterest();
    }//GEN-LAST:event_deletevehicledofinterestdata1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VehiclesOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VehiclesOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VehiclesOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VehiclesOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VehiclesOfInterestManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addnewvehicleofintrestdata;
    private javax.swing.JComboBox<String> colorselectionComboBox;
    private javax.swing.JButton deletevehicledofinterestdata1;
    private javax.swing.JButton helpinformationbuttonvoimanager;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton leavevehcileofinterestmanager;
    private javax.swing.JTextField licenseplateinputforviomanager;
    private javax.swing.JComboBox<String> makeselectionComboBox;
    private javax.swing.JComboBox<String> modelselectionComboBox;
    private javax.swing.JTextField ownersnameinputforvoimanager;
    private javax.swing.JTextField ownersphonenumberforvoimanager;
    private javax.swing.JComboBox<String> reasonselectionComboBox;
    private javax.swing.JButton resetvehicledofinterestdata;
    private javax.swing.JTextField searchvoimanagertxtfeild;
    private javax.swing.JButton updatedvehicleofinterestdata;
    private javax.swing.JTable vehiclesofinterestmanagertable;
    private javax.swing.JTextField vehicleyearinputforvoimanager;
    // End of variables declaration//GEN-END:variables
}
