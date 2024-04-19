/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VehiclesofInterestDesktopApplication;
import VehiclesofInterestDesktopApplication.VehiclesOfInterestController;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;  // Import JButton for using a JButton component
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Represents the ReasonsOfInterestManager JFrame.
 * Handles the management of reasons of interest.
 * 
 * @author Maria Stewart
 */


public class ReasonsOfInterestManager extends javax.swing.JFrame {
    /**
     * Creates new form VOICase
     */
    public ReasonsOfInterestManager() {
        initComponents();
        populateReasonOfInterestManagerTable();
        updateReasonOfInterestTable();
        handleTableSelection();
        updateReasonOfInterestData();
        resetReasonOfInterestData(); 
        
        
        
    // Add ListSelectionListener to the table
        reasonofinterestdatamanager.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Call a method to handle row selection
                    handleTableSelection();
                }
            }
        });

        // Initialize JButton
        updateReasonOfInterestDataButton = new javax.swing.JButton();

        // Add ActionListener to the "Update" button
        updateReasonOfInterestDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateReasonOfInterestData();
            }
        });  
        
 
    }

    private int selectedRowIndex = -1;
    
     private void populateReasonOfInterestManagerTable() {
        // Step 1: Create an instance of VehiclesOfInterestController
        DefaultTableModel model = (DefaultTableModel) reasonofinterestdatamanager.getModel();
        VehiclesOfInterestController controller = new VehiclesOfInterestController();
        
        //Step 2: Retrieve Data
        String[][] allReasonData = controller.getAllReasonsForInterest();
        
        //Clear exisiting rows
        model.setRowCount(0);
        
        //Step 3: Set Up JTable
        for (String[] rowData : allReasonData) {
            model.addRow(rowData);
        }
    }
     
     private void updateReasonOfInterestTable() {
        DefaultTableModel model = (DefaultTableModel) reasonofinterestdatamanager.getModel();
        
        // Clear existing rows
        model.setRowCount(0);
        
        //Add a new row to the tabel 
        populateReasonOfInterestManagerTable();  
        
    }
     
     
     //Handle event when a row is selected in the JTable 
/*
When a user clicks on a row in the table
the method is triggered to capture the data selected 
additionally populates various input feilds in UI
*/
private void handleTableSelection() {
    // Get the index of the selected row in the JTable
    int selectedRow = reasonofinterestdatamanager.getSelectedRow();
// Check if a row is selected
    if (selectedRow != -1) {
        // Retrieve data from the selected row in the JTable
         String reason = (String) reasonofinterestdatamanager.getValueAt(selectedRow, 0);
         String description = (String) reasonofinterestdatamanager.getValueAt(selectedRow, 1);

 // Set the retrieved data to corresponding input fields
        newreasoninputtextfeild.setText(reason);
        descriptioninputtextfeild.setText(description);
        
       // Update selectedRowIndex to store the index of the selected row
        selectedRowIndex = selectedRow; 
    }
}

//Update a selected row in the table 
private void updateReasonOfInterestData() {
   if (selectedRowIndex != -1) {
            // Corrected method calls to getText() and getText() instead of getTex() and getText
            String reason = newreasoninputtextfeild.getText();
            String description = descriptioninputtextfeild.getText();

            System.out.println("Updating with values:");
            System.out.println("Reason: " + reason);
            System.out.println("Description: " + description);

            VehiclesOfInterestController controller = new VehiclesOfInterestController();
            controller.updateReasonForInterest(reason, description);

            DefaultTableModel vehicleTableModel = (DefaultTableModel) reasonofinterestdatamanager.getModel();

            vehicleTableModel.setValueAt(reason, selectedRowIndex, 0);
            vehicleTableModel.setValueAt(description, selectedRowIndex, 1);

            reasonofinterestdatamanager.clearSelection();
        } else {
            System.out.println("No row selected for update.");
        }
    }
  
// Method to add a new reason and description
private void addNewReasonAndDescription() {
    String reason = newreasoninputtextfeild.getText();
    String description = descriptioninputtextfeild.getText();

     // Check if the 'Reason' and 'Description' fields are empty
    if (reason.trim().isEmpty() || description.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill out both 'Reason' and 'Description' fields.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Stop the method if any of the fields is empty
    }
    
    // Call the controller method to add a new reason of interest
    VehiclesOfInterestController controller = new VehiclesOfInterestController();
    controller.addNewReasonForInterest(reason, description);

    // Get the existing model from the table
    DefaultTableModel reasonTableModel = (DefaultTableModel) reasonofinterestdatamanager.getModel();

    // Add a new row to the table model
    reasonTableModel.addRow(new Object[]{reason, description});

    // Clear input fields after adding
    resetReasonOfInterestData();

    // Show a popup notification for successful addition
    JOptionPane.showMessageDialog(null, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
}


 private void deleteReasonOfInterest() {
    // Get the selected row from the table
    int selectedRow = reasonofinterestdatamanager.getSelectedRow();

    // Check if a row is selected
    if (selectedRow >= 0) {
        // Get the reason from the selected row
        String reason = reasonofinterestdatamanager.getValueAt(selectedRow, 0).toString();

        // Call the controller method to delete the selected reason of interest
        VehiclesOfInterestController controller = new VehiclesOfInterestController();
        controller.deleteReasonForInterestBreed(reason);

        // Show a popup notification for successful deletion
        JOptionPane.showMessageDialog(null, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Remove the selected row from the table model
        DefaultTableModel reasonTableModel = (DefaultTableModel) reasonofinterestdatamanager.getModel();
        reasonTableModel.removeRow(selectedRow);
    } else {
        // No row selected, show an error message or handle accordingly
        JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Clear input fields after deletion
    resetReasonOfInterestData();   
}

//Method to reset all feilds to start over entering new data 
 private void resetReasonOfInterestData() {
    newreasoninputtextfeild.setText("");
    descriptioninputtextfeild.setText("");
   

}
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reasonofinterestmanagerbackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        reasonofinterestinputfeilds = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptioninputtextfeild = new javax.swing.JTextArea();
        newreasoninputtextfeild = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        searchreasonofinterestmanager = new javax.swing.JTextField();
        exitreasonofinterestmanager = new javax.swing.JButton();
        addnewreasonanddescription = new javax.swing.JButton();
        deletereasonordescription = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        updateReasonOfInterestDataButton = new javax.swing.JButton();
        resetreasondofinterestdata = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reasonofinterestdatamanager = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        helpinformationbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reasonofinterestmanagerbackground.setBackground(new java.awt.Color(0, 102, 51));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\School\\SWEN 661\\Desktop Project code\\statepolicelogoresize.png")); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setText("Reasons of Interest Manager");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186)
                .addComponent(jLabel2)
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        reasonofinterestinputfeilds.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Description");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Reason");

        descriptioninputtextfeild.setColumns(20);
        descriptioninputtextfeild.setRows(5);
        jScrollPane1.setViewportView(descriptioninputtextfeild);

        javax.swing.GroupLayout reasonofinterestinputfeildsLayout = new javax.swing.GroupLayout(reasonofinterestinputfeilds);
        reasonofinterestinputfeilds.setLayout(reasonofinterestinputfeildsLayout);
        reasonofinterestinputfeildsLayout.setHorizontalGroup(
            reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reasonofinterestinputfeildsLayout.createSequentialGroup()
                .addGroup(reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reasonofinterestinputfeildsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reasonofinterestinputfeildsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addComponent(newreasoninputtextfeild))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        reasonofinterestinputfeildsLayout.setVerticalGroup(
            reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reasonofinterestinputfeildsLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newreasoninputtextfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(reasonofinterestinputfeildsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        searchreasonofinterestmanager.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchreasonofinterestmanagerKeyReleased(evt);
            }
        });

        exitreasonofinterestmanager.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        exitreasonofinterestmanager.setText("Exit");
        exitreasonofinterestmanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitreasonofinterestmanagerActionPerformed(evt);
            }
        });

        addnewreasonanddescription.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addnewreasonanddescription.setText("Add New");
        addnewreasonanddescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addnewreasonanddescriptionActionPerformed(evt);
            }
        });

        deletereasonordescription.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        deletereasonordescription.setText("Delete");
        deletereasonordescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletereasonordescriptionActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("Search Data");

        updateReasonOfInterestDataButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updateReasonOfInterestDataButton.setText("Update");
        updateReasonOfInterestDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateReasonOfInterestDataButtonActionPerformed(evt);
            }
        });

        resetreasondofinterestdata.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        resetreasondofinterestdata.setText("Reset");
        resetreasondofinterestdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetreasondofinterestdataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addnewreasonanddescription, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(updateReasonOfInterestDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resetreasondofinterestdata, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(deletereasonordescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 55, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(exitreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deletereasonordescription, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addnewreasonanddescription, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateReasonOfInterestDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetreasondofinterestdata, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(exitreasonofinterestmanager, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        reasonofinterestdatamanager.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Reason", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        reasonofinterestdatamanager.setShowGrid(true);
        jScrollPane2.setViewportView(reasonofinterestdatamanager);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setIcon(new javax.swing.ImageIcon("E:\\School\\helpicon.png")); // NOI18N
        jLabel18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel18KeyPressed(evt);
            }
        });

        helpinformationbutton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        helpinformationbutton.setText("Help");
        helpinformationbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpinformationbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reasonofinterestmanagerbackgroundLayout = new javax.swing.GroupLayout(reasonofinterestmanagerbackground);
        reasonofinterestmanagerbackground.setLayout(reasonofinterestmanagerbackgroundLayout);
        reasonofinterestmanagerbackgroundLayout.setHorizontalGroup(
            reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                .addGroup(reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                        .addGap(455, 455, 455)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(helpinformationbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                                .addComponent(reasonofinterestinputfeilds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reasonofinterestmanagerbackgroundLayout.setVerticalGroup(
            reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reasonofinterestinputfeilds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(reasonofinterestmanagerbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addGap(14, 14, 14))
                    .addGroup(reasonofinterestmanagerbackgroundLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(helpinformationbutton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reasonofinterestmanagerbackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reasonofinterestmanagerbackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addnewreasonanddescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addnewreasonanddescriptionActionPerformed
        // TODO add your handling code here:
        addNewReasonAndDescription(); 
    }//GEN-LAST:event_addnewreasonanddescriptionActionPerformed

    private void exitreasonofinterestmanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitreasonofinterestmanagerActionPerformed
        // Exit current screen and go back to the main page 
        Main mainpage = new Main();
        mainpage.show(); //display the main page here
        
        dispose(); //close current frame and go back to the main page 
    }//GEN-LAST:event_exitreasonofinterestmanagerActionPerformed

    private void updateReasonOfInterestDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateReasonOfInterestDataButtonActionPerformed
        // call on method to update 
        updateReasonOfInterestData();
    }//GEN-LAST:event_updateReasonOfInterestDataButtonActionPerformed

    private void searchreasonofinterestmanagerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchreasonofinterestmanagerKeyReleased
        // Create object for table model to search reason of interest on in the database
        DefaultTableModel ob = (DefaultTableModel) reasonofinterestdatamanager.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        reasonofinterestdatamanager.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchreasonofinterestmanager.getText()));
    }//GEN-LAST:event_searchreasonofinterestmanagerKeyReleased

    private void resetreasondofinterestdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetreasondofinterestdataActionPerformed
        //call method to reset all feilds
           resetReasonOfInterestData(); 
    }//GEN-LAST:event_resetreasondofinterestdataActionPerformed

    private void jLabel18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel18KeyPressed

    }//GEN-LAST:event_jLabel18KeyPressed

    private void helpinformationbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpinformationbuttonActionPerformed
        // This will provided a pop up window telling the user what to do on the screen
       
        // Create a custom dialog
    JDialog helpDialog = new JDialog(this, "Help Information", true);

    // Set the help message
    String helpMessage = "To search for a specific reason of interest, you can search for it by tpying in the search data bar. If you want to add a record, fill out the required information then click 'Add New'."
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
    }//GEN-LAST:event_helpinformationbuttonActionPerformed

    private void deletereasonordescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletereasonordescriptionActionPerformed
        // TODO add your handling code here:
        deleteReasonOfInterest();
    }//GEN-LAST:event_deletereasonordescriptionActionPerformed

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
            java.util.logging.Logger.getLogger(ReasonsOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReasonsOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReasonsOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReasonsOfInterestManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new ReasonsOfInterestManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addnewreasonanddescription;
    private javax.swing.JButton deletereasonordescription;
    private javax.swing.JTextArea descriptioninputtextfeild;
    private javax.swing.JButton exitreasonofinterestmanager;
    private javax.swing.JButton helpinformationbutton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField newreasoninputtextfeild;
    private javax.swing.JTable reasonofinterestdatamanager;
    private javax.swing.JPanel reasonofinterestinputfeilds;
    private javax.swing.JPanel reasonofinterestmanagerbackground;
    private javax.swing.JButton resetreasondofinterestdata;
    private javax.swing.JTextField searchreasonofinterestmanager;
    private javax.swing.JButton updateReasonOfInterestDataButton;
    // End of variables declaration//GEN-END:variables
}
