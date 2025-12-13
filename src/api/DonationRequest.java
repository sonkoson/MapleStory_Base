package api;

import database.DBConfig;
import database.DBConnection;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

public class DonationRequest extends JFrame {
   private JButton jButton1;
   private JButton jButton2;
   private JButton jButton3;
   private JButton jButton4;
   private JLabel jLabel1;
   private JScrollPane jScrollPane1;
   private static JTable jTable1;
   private JTextField jTextField1;

   public DonationRequest() {
      this.initComponents();
   }

   private void initComponents() {
      this.jScrollPane1 = new JScrollPane();
      jTable1 = new JTable();
      this.jLabel1 = new JLabel();
      this.jButton1 = new JButton();
      this.jTextField1 = new JTextField();
      this.jButton2 = new JButton();
      this.jButton3 = new JButton();
      this.jButton4 = new JButton();
      this.setDefaultCloseOperation(3);
      jTable1.setModel(
         new DefaultTableModel(
            new Object[][]{
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null},
               {null, null, null, null, null, null, null, null}
            },
            new String[]{"id", "เนยย เนเธ’เธ เนยยเนเธย", "เนเธ“ยเนย โ€ข ID", "เนเธยเนเธเธเนยเธ เนยเธ”เนเธย", "เนยโ€ฆเนเธยเนยยเนเธโ€ฆ", "เนยย เนเธ’เธ เนยเธเนยเธเนยเธ", "เนยย เนยโ€ข", "เนยยเนยย"}
         ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
               return this.canEdit[columnIndex];
            }
         }
      );
      jTable1.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
            DonationRequest.this.jTable1MouseClicked(evt);
         }
      });
      this.jScrollPane1.setViewportView(jTable1);
      if (jTable1.getColumnModel().getColumnCount() > 0) {
         jTable1.getColumnModel().getColumn(0).setMinWidth(50);
         jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
         jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
      }

      this.jLabel1.setText("เนยเธเนยเธเนยเธ เนเธ–เธเนย ย เนยย เนเธ’เธ เนยเธ”เนโ€”เธ -");
      this.jButton1.setText("เนยยเนเธยเนเธ“ย เนเธเธ");
      this.jButton1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            DonationRequest.this.jButton1ActionPerformed(evt);
         }
      });
      this.jTextField1.setFont(new Font("เนยยเนยโ€เนเธ“ย เนโ€โ€ข", 0, 18));
      this.jTextField1.setText("เนยย เนยยเนยย เนยโฌเนยย");
      this.jButton2.setText("เนเธ–เธเนย ย เนยยเนเธย");
      this.jButton2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            DonationRequest.this.jButton2ActionPerformed(evt);
         }
      });
      this.jButton3.setText("เนเธเธเนเธ’ยเนเธเธ");
      this.jButton3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            DonationRequest.this.jButton3ActionPerformed(evt);
         }
      });
      this.jButton4.setText("เนยโ€ฆเนเธย เนยยเนยเธ");
      this.jButton4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
            DonationRequest.this.jButton4ActionPerformed(evt);
         }
      });
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.jScrollPane1)
                        .addGroup(
                           layout.createSequentialGroup()
                              .addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jTextField1, -2, 241, -2).addComponent(this.jLabel1))
                              .addPreferredGap(ComponentPlacement.UNRELATED)
                              .addComponent(this.jButton2, -2, 103, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addComponent(this.jButton3, -2, 103, -2)
                              .addPreferredGap(ComponentPlacement.RELATED)
                              .addGroup(
                                 layout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup().addGap(0, 198, 32767).addComponent(this.jButton1))
                                    .addGroup(layout.createSequentialGroup().addComponent(this.jButton4, -2, 103, -2).addGap(0, 0, 32767))
                              )
                        )
                  )
                  .addContainerGap()
            )
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(Alignment.LEADING)
            .addGroup(
               Alignment.TRAILING,
               layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jButton1))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addGroup(
                     layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(this.jButton4, -1, -1, 32767)
                        .addComponent(this.jButton2, -1, -1, 32767)
                        .addComponent(this.jTextField1, -1, 38, 32767)
                        .addComponent(this.jButton3, -1, -1, 32767)
                  )
                  .addPreferredGap(ComponentPlacement.UNRELATED)
                  .addComponent(this.jScrollPane1, -2, 515, -2)
                  .addGap(18, 18, 18)
            )
      );
      this.pack();
   }

   private void jButton1ActionPerformed(ActionEvent evt) {
      init();
   }

   private void jTable1MouseClicked(MouseEvent evt) {
      Object obj = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1);
      if (obj != null) {
         Object obj2 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2);
         String accountName = (String)obj2;
         this.jTextField1.setText(accountName);
      }
   }

   private void jButton2ActionPerformed(ActionEvent evt) {
      if (jTable1.getSelectedRow() >= 0) {
         Object obj = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 7);
         jTable1.getModel().setValueAt("เนเธ’ยเนเธเธเนยยเนเธย", jTable1.getSelectedRow(), 7);
         PreparedStatement ps = null;

         try (Connection con = DBConnection.getConnection()) {
            Object obj2 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2);
            Object obj3 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
            String accountName = (String)obj2;
            int id = (Integer)obj3;
            ps = con.prepareStatement("UPDATE `donation_request` SET `status` = ? WHERE `account_name` = ? AND `id` = ?");
            ps.setInt(1, 1);
            ps.setString(2, accountName);
            ps.setInt(3, id);
            ps.executeUpdate();
         } catch (SQLException var21) {
         } finally {
            try {
               if (ps != null) {
                  ps.close();
                  PreparedStatement var23 = null;
               }
            } catch (SQLException var18) {
            }
         }
      }
   }

   private void jButton3ActionPerformed(ActionEvent evt) {
      if (jTable1.getSelectedRow() >= 0) {
         Object obj = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 7);
         jTable1.getModel().setValueAt("เนเธเธเนเธ’ยเนเธเธ", jTable1.getSelectedRow(), 7);
         PreparedStatement ps = null;

         try (Connection con = DBConnection.getConnection()) {
            Object obj2 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2);
            Object obj3 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
            String accountName = (String)obj2;
            int id = (Integer)obj3;
            ps = con.prepareStatement("UPDATE `donation_request` SET `status` = ? WHERE `account_name` = ? AND `id` = ?");
            ps.setInt(1, 0);
            ps.setString(2, accountName);
            ps.setInt(3, id);
            ps.executeUpdate();
         } catch (SQLException var21) {
         } finally {
            try {
               if (ps != null) {
                  ps.close();
                  PreparedStatement var23 = null;
               }
            } catch (SQLException var18) {
            }
         }
      }
   }

   private void jButton4ActionPerformed(ActionEvent evt) {
      if (jTable1.getSelectedRow() >= 0) {
         Object obj = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 7);
         jTable1.getModel().setValueAt("เนยโ€ฆเนเธยเนยยเนยเธ", jTable1.getSelectedRow(), 7);
         PreparedStatement ps = null;

         try (Connection con = DBConnection.getConnection()) {
            Object obj2 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2);
            Object obj3 = jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
            String accountName = (String)obj2;
            int id = (Integer)obj3;
            ps = con.prepareStatement("UPDATE `donation_request` SET `status` = ? WHERE `account_name` = ? AND `id` = ?");
            ps.setInt(1, 2);
            ps.setString(2, accountName);
            ps.setInt(3, id);
            ps.executeUpdate();
         } catch (SQLException var21) {
         } finally {
            try {
               if (ps != null) {
                  ps.close();
                  PreparedStatement var23 = null;
               }
            } catch (SQLException var18) {
            }
         }
      }
   }

   public static void init() {
      if (!DBConfig.isGanglim) {
         DefaultTableModel m = (DefaultTableModel)jTable1.getModel();

         for (int i = m.getRowCount() - 1; i >= 0; i--) {
            m.removeRow(i);
         }

         PreparedStatement ps = null;
         ResultSet rs = null;

         try (Connection con = DBConnection.getConnection()) {
            ps = con.prepareStatement("SELECT * FROM `donation_request` ORDER BY `id` DESC");
            rs = ps.executeQuery();
            int index = 0;

            while (rs.next()) {
               Timestamp time = rs.getTimestamp("time");
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String fDate = sdf.format(time.getTime());
               String status = "";
               int s = rs.getInt("status");
               if (s == 0) {
                  status = "เนเธเธเนเธ’ยเนเธเธ";
               } else if (s == 1) {
                  status = "เนเธ’ยเนเธเธเนยยเนเธย";
               } else if (s == 2) {
                  status = "เนยโ€ฆเนเธยเนยยเนยเธ";
               }

               int t = rs.getInt("type");
               String type = "";
               if (t == 0) {
                  type = "เนยเธเนเธย";
               } else if (t == 1) {
                  type = "เนยเธ”เนเธ’เธเนยเธ";
               } else if (t == 2) {
                  type = "เนเธ”ยเนยเธเนยยเนยเธเนยเธเนเธโฌ";
               } else if (t == 3) {
                  type = "เนเธ“เธ”เนยยเนยเธเนยเธ”เนเธ’เธเนยเธ";
               } else if (t == 5) {
                  type = "เนยเธเนยย A";
               } else if (t == 6) {
                  type = "เนยเธเนยย B";
               } else if (t == 7) {
                  type = "เนยเธเนยย C";
               } else if (t == 8) {
                  type = "เนโ€“เธ”เนเธเธเนยเธ”เนยย ";
               } else if (t == 9) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธC";
               } else if (t == 10) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธB";
               } else if (t == 11) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธA";
               } else if (t == 12) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธS";
               } else if (t == 13) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธSS";
               } else if (t == 14) {
                  type = "เนเธโฌเนย โ€ขเนยยเนยเธSSS";
               } else if (t == 15) {
                  type = "เนยยเนยยเนยเธเนยเธเนเธโฌ1";
               } else if (t == 16) {
                  type = "เนยยเนยยเนยเธเนยเธเนเธโฌ2";
               } else if (t == 17) {
                  type = "เนเธ–โ€เนยยเนยเธเนยเธเนเธโฌI";
               } else if (t == 18) {
                  type = "เนเธ–โ€เนยยเนยเธเนยเธเนเธโฌII";
               } else if (t == 19) {
                  type = "เนเธ–โ€เนยยเนยเธเนยเธเนเธโฌIII";
               } else if (t == 20) {
                  type = "เนเธ–โ€เนยยเนยเธเนยเธเนเธโฌ IV";
               } else if (t == 21) {
                  type = "3เนเธเธเนโ€ฆยเนยเธเนยเธเนเธโฌI";
               } else if (t == 22) {
                  type = "3เนเธเธเนโ€ฆยเนยเธเนยเธเนเธโฌII";
               } else if (t == 23) {
                  type = "3เนเธเธเนโ€ฆยเนยเธเนยเธเนเธโฌIII";
               } else if (t == 24) {
                  type = "เนยเธเนเธเธเนยเธเนเธยเนยเธเนยเธเนยเธเนเธโฌ1";
               } else if (t == 25) {
                  type = "เนยเธเนเธเธเนยเธเนเธยเนยเธเนยเธเนยเธเนเธโฌ2";
               } else if (t == 26) {
                  type = "เนยเธเนเธเธเนยเธเนเธยเนยเธเนยเธเนยเธเนเธโฌ3";
               } else if (t == 27) {
                  type = "เนยเธเนเธเธเนยเธเนเธยเนยเธเนยเธเนยเธเนเธโฌ4";
               } else if (t == 28) {
                  type = "2023เนยเธเนยเธเนเธโฌ1";
               } else if (t == 29) {
                  type = "2023เนยเธเนยเธเนเธโฌ2";
               } else if (t == 30) {
                  type = "2023เนยเธเนยเธเนเธโฌ3";
               } else if (t == 31) {
                  type = "2023เนยเธเนยเธเนเธโฌ4";
               } else if (t == 32) {
                  type = "2023เนยเธเนยเธเนเธโฌ5";
               } else if (t == 33) {
                  type = "2023เนยเธเนยเธเนเธโฌ6";
               } else if (t == 34) {
                  type = "2023เนยเธเนยเธเนเธโฌ7";
               } else if (t == 35) {
                  type = "2023เนยเธเนยเธเนเธโฌ8";
               } else if (t == 36) {
                  type = "2023เนยเธเนยเธเนเธโฌ9";
               } else if (t == 37) {
                  type = "2023เนยเธเนยเธเนเธโฌ10";
               } else if (t == 38) {
                  type = "2023เนยเธเนยเธเนเธโฌ11";
               } else if (t == 39) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ1";
               } else if (t == 40) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ2";
               } else if (t == 41) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ3";
               } else if (t == 42) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ4";
               } else if (t == 43) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ5";
               } else if (t == 44) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ6";
               } else if (t == 45) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ7";
               } else if (t == 46) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ8";
               } else if (t == 47) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ9";
               } else if (t == 48) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ10";
               } else if (t == 49) {
                  type = "5เนยโ€เนยเธเนยเธเนเธโฌ11";
               }

               m.insertRow(
                  index++,
                  new Object[]{
                     rs.getInt("id"),
                     fDate,
                     rs.getString("account_name"),
                     rs.getString("player_name"),
                     rs.getString("real_name"),
                     rs.getInt("point"),
                     type,
                     status
                  }
               );
            }
         } catch (SQLException var24) {
         } finally {
            try {
               if (ps != null) {
                  ps.close();
                  PreparedStatement var27 = null;
               }

               if (rs != null) {
                  rs.close();
                  ResultSet var28 = null;
               }
            } catch (SQLException var21) {
            }
         }
      }
   }

   public static void main() {
      try {
         for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException var4) {
         Logger.getLogger(DonationRequest.class.getName()).log(Level.SEVERE, null, var4);
      } catch (InstantiationException var5) {
         Logger.getLogger(DonationRequest.class.getName()).log(Level.SEVERE, null, var5);
      } catch (IllegalAccessException var6) {
         Logger.getLogger(DonationRequest.class.getName()).log(Level.SEVERE, null, var6);
      } catch (UnsupportedLookAndFeelException var7) {
         Logger.getLogger(DonationRequest.class.getName()).log(Level.SEVERE, null, var7);
      }

      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            new DonationRequest().setVisible(true);
         }
      });
   }
}
