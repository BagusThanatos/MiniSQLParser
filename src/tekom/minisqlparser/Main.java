/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BagusThanatos
 */
public class Main extends javax.swing.JFrame {

    private boolean help=false,inputLexical=false,inputQuery=false;
    private String query;
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        this.query="";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonInputQuery = new javax.swing.JButton();
        buttonLexicalCode = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMain = new javax.swing.JTable();
        labelResult = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        menuLexicalCodeList = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonInputQuery.setText("Input Query");
        buttonInputQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInputQueryActionPerformed(evt);
            }
        });

        buttonLexicalCode.setText("Input Lexical Code");
        buttonLexicalCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLexicalCodeActionPerformed(evt);
            }
        });

        tableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "String", "Besaran Lexic", "Token Lexic"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableMain);

        jMenu1.setText("File");

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        menuLexicalCodeList.setText("Help");

        jMenuItem1.setText("Lexical Code List");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuLexicalCodeList.add(jMenuItem1);

        jMenuBar1.add(menuLexicalCodeList);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(buttonInputQuery)
                        .addGap(31, 31, 31)
                        .addComponent(buttonLexicalCode))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(labelResult, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonInputQuery)
                    .addComponent(buttonLexicalCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelResult, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_menuExitActionPerformed

    private void buttonInputQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInputQueryActionPerformed
        if (!this.inputQuery){
            new InputQuery(this).setVisible(true);
            this.inputQuery=true;
        }
    }//GEN-LAST:event_buttonInputQueryActionPerformed

    private void buttonLexicalCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLexicalCodeActionPerformed
        if (!this.inputLexical) {
            new InputLexicalCode(this).setVisible(true);
            this.inputLexical=true;
        }
    }//GEN-LAST:event_buttonLexicalCodeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(!this.help){
            new Help(this).setVisible(true);
            this.help=true;
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void setHelp(boolean h){
        this.help=h;
    }
    public void setInputQuery(boolean s){
        this.inputQuery=s;
    }
    public void setInputLexical(boolean s) {
        this.inputLexical=s;
    }
    public void setQuery(String s,boolean isLexicalCode){
        this.query=s;
        this.setTable(s,isLexicalCode);
    }
    private void removeTableValues(){
        DefaultTableModel d = (DefaultTableModel) this.tableMain.getModel();
        while (d.getRowCount()>0) d.removeRow(0);
    }
    private void setTable(String s,boolean isLexicalCode){
        if (s.isEmpty()) return;
        boolean valid;
        removeTableValues();
        DefaultTableModel d = (DefaultTableModel) this.tableMain.getModel();
        if (!isLexicalCode){
            ArrayList<TokenLexic> r = Parser.parseSQL(s);
            r.stream().forEach((r_) -> { 
                d.addRow(new String[]{r_.getValue()+"",r_.getName(),r_.getTokenCode()+""});
            });   
            try {
                valid = Parser.isValid(Parser.toArrayInt(r));
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                valid = false;
            }
        }
        else {
            ArrayList<Integer> a = Parser.parseLexical(s);
            a.stream().forEach((Integer a_) ->{
                d.addRow(new String[]{"","",a_.toString()});
            });
            try {
                valid = Parser.isValid(a);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                valid=false;
            }
        }
        if (valid) {
            this.labelResult.setText("VALID");
            this.labelResult.setBackground(Color.green);
        }
        else {
            this.labelResult.setText("TIDAK VALID");
            this.labelResult.setBackground(Color.red);
        }
    }
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonInputQuery;
    private javax.swing.JButton buttonLexicalCode;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelResult;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuLexicalCodeList;
    private javax.swing.JTable tableMain;
    // End of variables declaration//GEN-END:variables
}
