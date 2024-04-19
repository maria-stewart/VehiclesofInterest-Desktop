/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VehiclesofInterestDesktopApplication;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author moore
 */
public class VehicleMakeandModelManager extends javax.swing.JFrame {
    
    /**
     * Creates new form NewJFrame
     */
    public VehicleMakeandModelManager() {
        initComponents();
        populateMakeAndModelManagerTable();
        updateMakeandModelManagerTable(); 
        handleTableSelection(); 
        updateMakeAndModelData();  
        resetMakeAndModelData();
        
    // Add ListSelectionListener to the table
        makeandmodelmanagertabel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Call a method to handle row selection
                    handleTableSelection();
                }
            }
        });  

        // Initialize JButton
        updateVehicleMakeAndModelJButton = new javax.swing.JButton();

        // Add ActionListener to the "Update" button
        updateVehicleMakeAndModelJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMakeAndModelData();
            }
        });       
    }

    private int selectedRowIndex = -1;
    private void populateMakeAndModelManagerTable() {
       // Step 1: Create an instance of VehiclesOfInterestController
        VehiclesOfInterestController controller = new VehiclesOfInterestController();
        String [][] allVehicleModels = controller.allVehicleModel();
       
       //Create a Default Table Model
       DefaultTableModel model = (DefaultTableModel) makeandmodelmanagertabel.getModel();
       
       //Clear existing rows
        model.setRowCount(0);
        
       //Changes to the table need to be kept track of 
       Set<String> addedMakes = new HashSet<>();
       
       //Step 2: Retrieve all data for both make and model
       for (String[] rowData : allVehicleModels) {
           String modelStr = rowData[0];
           String make = rowData[1];
           
           String[] updatedRowData = new String[]{make, modelStr};
           model.addRow(updatedRowData);
           
           //Add the madke to the hashset of added makes if not present already
           if(!addedMakes.contains(make))
               addedMakes.add(make);
       }
       
       
       //Step 3: Set up the JTable 
       
       String [] allMakes = controller.allVehicleMake();
       
       for(String currentMake : allMakes) {
           if (!addedMakes.contains(currentMake)) {
               String[] updatedRowData = new String[] {currentMake, ""};
               model.addRow(updatedRowData);
           }
       }       
    }
    
    private void updateMakeandModelManagerTable() {
        DefaultTableModel model = (DefaultTableModel) makeandmodelmanagertabel.getModel();
        
        // Clear existing rows
        model.setRowCount(0);
        
        //Add a new row to the tabel 
         populateMakeAndModelManagerTable(); 
    }
    
     //Handle event when a row is selected in the JTable 
/*
When a user clicks on a row in the table
the method is triggered to capture the data selected 
additionally populates various input feilds in UI
*/
private void handleTableSelection() {
    // Get the index of the selected row in the JTable
    int selectedRow = makeandmodelmanagertabel.getSelectedRow();
// Check if a row is selected
    if (selectedRow != -1) {
        // Retrieve data from the selected row in the JTable
         String make = (String) makeandmodelmanagertabel.getValueAt(selectedRow, 0);
         String model = (String) makeandmodelmanagertabel.getValueAt(selectedRow, 1);

 // Set the retrieved data to corresponding input fields
        newmakeinputtxtfeild.setText(make);
        newmodelinputtxtfeild.setText(model);
        
       // Update selectedRowIndex to store the index of the selected row
        selectedRowIndex = selectedRow; 
    }
}

//Update a selected row in the table
//Update a selected row in the table 
private void updateMakeAndModelData() {
   if (selectedRowIndex != -1) {
            // Corrected method calls to getText() and getText() instead of getTex() and getText
            String make = newmakeinputtxtfeild.getText();
            String model = newmodelinputtxtfeild.getText();
            
            // Check if either make or model is empty
        if (model.isEmpty()) {
            // Show an error message if either make or model is empty
            JOptionPane.showMessageDialog(this, "The Model feild cannot be empty!.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without updating the record
        }

            System.out.println("Updating with values:");
            System.out.println("Make: " + make);
            System.out.println("Model: " + model);

            VehiclesOfInterestController controller = new VehiclesOfInterestController();
            controller.updateVehicleMakeAndModel(make, model);

            DefaultTableModel vehicleTableModel = (DefaultTableModel) makeandmodelmanagertabel.getModel();

            vehicleTableModel.setValueAt(make, selectedRowIndex, 0);
            vehicleTableModel.setValueAt(model, selectedRowIndex, 1);
    // Show a popup notification for successful deletion
    JOptionPane.showMessageDialog(this, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            makeandmodelmanagertabel.clearSelection();
        } else {
            System.out.println("No row selected for update.");
        }
    }

//Method to reset all feilds to start over entering new data 
 private void resetMakeAndModelData() {
    newmakeinputtxtfeild.setText("");
    newmodelinputtxtfeild.setText("");
   
}
    private void addNewMakeAndModel() {
    String make = newmakeinputtxtfeild.getText();
    String model = newmodelinputtxtfeild.getText();

    // Check if both make and model are filled out
    if (!make.isEmpty()) {
    } else {
        // Show an error message if either make or model is empty
        JOptionPane.showMessageDialog(this, "The Make feild cannot be empty!.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method without adding a new record
        }

    // Call the controller method to add a new make and model
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    controller.createVehicleMakeAndModel(make, model);

    // Get the existing model from the table
    DefaultTableModel makeAndModelTableModel = (DefaultTableModel) makeandmodelmanagertabel.getModel();

    // Add a new row to the table model
    makeAndModelTableModel.addRow(new Object[]{make, model});

    // Clear input fields after adding
    resetMakeAndModelData();

    // Show a popup notification for successful addition
    JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
}
    
    private void deleteMakeAndModel() {
    // Get the selected row index
    int selectedRowIndex = makeandmodelmanagertabel.getSelectedRow();

    // Check if a row is selected
    if (selectedRowIndex == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method if no row is selected
    }

    // Get the make and model values from the selected row
    String makeToDelete = (String) makeandmodelmanagertabel.getValueAt(selectedRowIndex, 0);
    String modelToDelete = (String) makeandmodelmanagertabel.getValueAt(selectedRowIndex, 1);

    // Call the controller method to delete the selected make and model
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    controller.deleteVehicleMakeAndModel(makeToDelete, modelToDelete);

    // Remove the selected row from the table model
    DefaultTableModel makeAndModelTableModel = (DefaultTableModel) makeandmodelmanagertabel.getModel();
    makeAndModelTableModel.removeRow(selectedRowIndex);

    // Show a popup notification for successful deletion
    JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        vehicleofinterestinputfeilds = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        newmodelinputtxtfeild = new javax.swing.JTextField();
        newmakeinputtxtfeild = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        exitreasonofinterestmanager = new javax.swing.JButton();
        addnewmakeormodel = new javax.swing.JButton();
        deletemakeormodel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        updateVehicleMakeAndModelJButton = new javax.swing.JButton();
        resetreasondofinterestdata = new javax.swing.JButton();
        searchmakeandmodelmanagertxtfeild = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        makeandmodelmanagertabel = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        helpinformationbuttonmakeandmodelmanager = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\School\\SWEN 661\\Desktop Project code\\statepolicelogoresize.png")); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setText("Vehicle Make/Model Manager");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jLabel2)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        vehicleofinterestinputfeilds.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Model");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Make");

        javax.swing.GroupLayout vehicleofinterestinputfeildsLayout = new javax.swing.GroupLayout(vehicleofinterestinputfeilds);
        vehicleofinterestinputfeilds.setLayout(vehicleofinterestinputfeildsLayout);
        vehicleofinterestinputfeildsLayout.setHorizontalGroup(
            vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehicleofinterestinputfeildsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vehicleofinterestinputfeildsLayout.createSequentialGroup()
                        .addComponent(newmodelinputtxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addComponent(newmakeinputtxtfeild))
                .addContainerGap())
        );
        vehicleofinterestinputfeildsLayout.setVerticalGroup(
            vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehicleofinterestinputfeildsLayout.createSequentialGroup()
                .addGroup(vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(vehicleofinterestinputfeildsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newmodelinputtxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vehicleofinterestinputfeildsLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(vehicleofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newmakeinputtxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        exitreasonofinterestmanager.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        exitreasonofinterestmanager.setText("Exit");
        exitreasonofinterestmanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitreasonofinterestmanagerActionPerformed(evt);
            }
        });

        addnewmakeormodel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addnewmakeormodel.setText("Add New");
        addnewmakeormodel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addnewmakeormodelActionPerformed(evt);
            }
        });

        deletemakeormodel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deletemakeormodel.setText("Delete");
        deletemakeormodel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletemakeormodelActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("Search Data");

        updateVehicleMakeAndModelJButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateVehicleMakeAndModelJButton.setText("Update");
        updateVehicleMakeAndModelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateVehicleMakeAndModelJButtonActionPerformed(evt);
            }
        });

        resetreasondofinterestdata.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetreasondofinterestdata.setText("Reset");
        resetreasondofinterestdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetreasondofinterestdataActionPerformed(evt);
            }
        });

        searchmakeandmodelmanagertxtfeild.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchmakeandmodelmanagertxtfeildKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(exitreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(searchmakeandmodelmanagertxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addnewmakeormodel, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(updateVehicleMakeAndModelJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetreasondofinterestdata, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(deletemakeormodel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(searchmakeandmodelmanagertxtfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addnewmakeormodel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletemakeormodel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateVehicleMakeAndModelJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetreasondofinterestdata, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(exitreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        makeandmodelmanagertabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Make", "Model"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        makeandmodelmanagertabel.setShowGrid(true);
        jScrollPane1.setViewportView(makeandmodelmanagertabel);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setIcon(new javax.swing.ImageIcon("E:\\School\\helpicon.png")); // NOI18N
        jLabel18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel18KeyPressed(evt);
            }
        });

        helpinformationbuttonmakeandmodelmanager.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        helpinformationbuttonmakeandmodelmanager.setText("Help");
        helpinformationbuttonmakeandmodelmanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpinformationbuttonmakeandmodelmanagerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(vehicleofinterestinputfeilds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(helpinformationbuttonmakeandmodelmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vehicleofinterestinputfeilds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(helpinformationbuttonmakeandmodelmanager)))
                .addGap(21, 21, 21))
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

    private void exitreasonofinterestmanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitreasonofinterestmanagerActionPerformed
        // Exit current screen and go back to the main page
        Main mainpage = new Main();
        mainpage.show(); //display the main page here

        dispose(); //close current frame and go back to the main page
    }//GEN-LAST:event_exitreasonofinterestmanagerActionPerformed

    private void addnewmakeormodelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addnewmakeormodelActionPerformed
        // TODO add your handling code here:
        addNewMakeAndModel();
    }//GEN-LAST:event_addnewmakeormodelActionPerformed

    private void updateVehicleMakeAndModelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateVehicleMakeAndModelJButtonActionPerformed
        // call on method to update
      updateMakeAndModelData(); 
    }//GEN-LAST:event_updateVehicleMakeAndModelJButtonActionPerformed

    private void resetreasondofinterestdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetreasondofinterestdataActionPerformed
        //call method to reset all feilds
        resetMakeAndModelData(); 
    }//GEN-LAST:event_resetreasondofinterestdataActionPerformed

    private void searchmakeandmodelmanagertxtfeildKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchmakeandmodelmanagertxtfeildKeyReleased
        // Creat object for the table to search make or model in database 
        DefaultTableModel ob = (DefaultTableModel)makeandmodelmanagertabel.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob); 
        makeandmodelmanagertabel.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchmakeandmodelmanagertxtfeild.getText()));
    }//GEN-LAST:event_searchmakeandmodelmanagertxtfeildKeyReleased

    private void deletemakeormodelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletemakeormodelActionPerformed
        // TODO add your handling code here:
        deleteMakeAndModel();
    }//GEN-LAST:event_deletemakeormodelActionPerformed

    private void jLabel18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel18KeyPressed

    }//GEN-LAST:event_jLabel18KeyPressed

    private void helpinformationbuttonmakeandmodelmanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpinformationbuttonmakeandmodelmanagerActionPerformed
        // This will provided a pop up window telling the user what to do on the screen
        // This will provided a pop up window telling the user what to do on the screen
       
        // Create a custom dialog
    JDialog helpDialog = new JDialog(this, "Help Information", true);

    // Set the help message
    String helpMessage = "To search for a specific make or model, you can search for it by tpying in the search data bar. If you want to add a record, fill out the required information then click 'Add New'."
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
    }//GEN-LAST:event_helpinformationbuttonmakeandmodelmanagerActionPerformed

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
            java.util.logging.Logger.getLogger(VehicleMakeandModelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VehicleMakeandModelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VehicleMakeandModelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VehicleMakeandModelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VehicleMakeandModelManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addnewmakeormodel;
    private javax.swing.JButton deletemakeormodel;
    private javax.swing.JButton exitreasonofinterestmanager;
    private javax.swing.JButton helpinformationbuttonmakeandmodelmanager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable makeandmodelmanagertabel;
    private javax.swing.JTextField newmakeinputtxtfeild;
    private javax.swing.JTextField newmodelinputtxtfeild;
    private javax.swing.JButton resetreasondofinterestdata;
    private javax.swing.JTextField searchmakeandmodelmanagertxtfeild;
    private javax.swing.JButton updateVehicleMakeAndModelJButton;
    private javax.swing.JPanel vehicleofinterestinputfeilds;
    // End of variables declaration//GEN-END:variables
}
