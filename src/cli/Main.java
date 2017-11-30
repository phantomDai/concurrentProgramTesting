package cli;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phantom
 */
public class Main extends JFrame {
    /*Variables declaration*/
    private JButton jButton1;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;
    private JCheckBox jCheckBox4;
    private JCheckBox jCheckBox5;
    private JLabel jLabel1;

    /*Store names of SUT*/
    public static List<String> SUTNames = new ArrayList<String>();


    public Main() { initComponents(); }

    /*initialize the form*/
    private void initComponents(){
        /*initialize jCheckBoxs*/
        jCheckBox1 = new JCheckBox();
        jCheckBox2 = new JCheckBox();
        jCheckBox3 = new JCheckBox();
        jCheckBox4 = new JCheckBox();
        jCheckBox5 = new JCheckBox();

        jLabel1 = new JLabel();
        jButton1 = new JButton();

        /*set close the window when click the close button*/
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*set show frame*/
        setVisible(true);

        /*set the frame to appear in the middle of the screen*/
        setLocationRelativeTo(null);

        /*set JCheckBox listener*/
        jCheckBox1.setText("SimpleLinear");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("SimpleTree");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("SequentialHeap");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("SequentialHeap");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("SequentialHeap");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jCheckBox5ActionPerformed(evt);
            }
        });

        /*set JLable txt*/
        jLabel1.setText("Please choose subjects:");

        /*set jButton txt*/
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                jButton1ActionPerformed(evt);
            }
        });

        /*set up layout*/
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jCheckBox4)
                                                        .addComponent(jCheckBox1))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jCheckBox5)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jCheckBox2)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jButton1)
                                                                        .addComponent(jCheckBox3))))
                                                .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBox3)
                                        .addComponent(jCheckBox2)
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBox4)
                                        .addComponent(jCheckBox5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt){
        SUTNames.add("SimpleLinear");
    }

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt){
        SUTNames.add("SimpleTree");
    }

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt){
        SUTNames.add("SequentialHeap");
    }

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt){
        SUTNames.add("FineGrainedHeap");
    }

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt){
        SUTNames.add("SkipQueue");
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
        dispose();
        TestBegin.begintest(Main.SUTNames);
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

}
