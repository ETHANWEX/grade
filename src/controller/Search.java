package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Search extends JFrame implements ActionListener {
    JPanel contain;
    JLabel id;
    JTextField idt;
    JButton search;

    String table;
    HashMap<String,String[]> map;

    public Search(String table, HashMap dictionary){
        super("��ѯ");
        setSize(300, 340);
        setLocation(600, 400);
        contain = new JPanel();
        contain.setLayout(null);
        add(contain);
        id = new JLabel("�γ̺�");
        idt = new JTextField();
        search = new JButton("��ѯ");
        id.setBounds(38, 50, 75, 35);
        idt.setBounds(80, 50, 150, 35);
        search.setBounds(102, 125, 70, 30);
        contain.add(id);
        contain.add(idt);
        contain.add(search);
        search.addActionListener(this);
        setVisible(true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        this.map=dictionary;
        this.table=table;
    }

    void output(String courseid){
        JFrame fm = new JFrame("�鿴�ض��γ�");
        fm.setLocation(600, 400);
        JPanel ct = new JPanel();
        ct.setLayout(new BorderLayout());
        JTextArea newlist = new JTextArea();
        newlist.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(newlist);

        if (table=="course"){
            fm.setSize(330, 400);
            newlist.append("�γ̱��\t�γ���\tѧ��\tѧʱ\n");
            try{
                String s = null;
                String path1 = System.getProperty("user.dir")+"/data/course.txt";
                BufferedReader br1 = new BufferedReader(
                        new FileReader(path1));        // ����һ��BufferedReader������ȡ�ļ�

                while ((s = br1.readLine()) != null) { // ʹ��readLine������һ�ζ�һ��
                    String[] result1 = s.split(" ");
                    if (courseid.equals(result1[0])) {
                        newlist.append(courseid + "\t");
                        newlist.append(result1[1] + "\t");
                        newlist.append(result1[2] + "\t");
                        newlist.append(result1[3] + "\n");
                        break;
                    }
                }
                br1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            fm.setSize(600, 400);
            newlist.append("�γ̺�" + "\t");
            newlist.append("�γ���" + "\t");
            newlist.append("��ʦ����" + "\t");
            newlist.append("��ʦ����" + "\t");
            newlist.append("ѧ��" + "\t");
            newlist.append("ѧ������" + "\t");
            newlist.append("�ɼ�" + "\n");
            String[] result=map.get(courseid);
            newlist.append(result[0] + "\t"+result[1] + "\t"+result[2] + "\t"+result[3] + "\t"+result[4]+"\t"+result[5]+"\t"+result[6]+"\n");
        }
        ct.add(scrollPane, BorderLayout.CENTER); // �� JScrollPane ��ӵ���������
        fm.add(ct);
        fm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        //��������ѯ��ť�򴥷�
        if (e.getSource() == search){
            if (map.containsKey(idt.getText())){
                output(idt.getText());
            }else{
                JOptionPane.showMessageDialog(null, "���ſγ̲�����ķ�Χ�ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


}
