package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeAppeal extends JFrame implements ActionListener {
    //����ɼ���������
    JPanel contain;
    JButton check,appeal;
    //����map���ڼ��
    HashMap<String,String[]> gradeMap;
    HashMap<String,String> appealMap;
    String id;


    //����ɼ��������
    public GradeAppeal(HashMap gradeMap,String id) {
        super("�ɼ�����");
        setLocation(300, 200);
        setSize(300, 300);
        this.gradeMap = gradeMap;
        this.id = id;
        contain = new JPanel();
        contain.setLayout(null);
        //�鿴������ť
        check = new JButton("�鿴����");
        check.setBounds(70, 140, 140, 50);
        //�ύ���߰�ť
        appeal=new JButton("�ύ����");
        appeal.setBounds(70, 40, 140, 50);

        contain.add(check);
        contain.add(appeal);

        appeal.addActionListener(this);
        check.addActionListener(this);

        //��ѯ�����ļ���ȡ���������й���Ϣ
        appealMap=new HashMap<String,String>();

        String path = System.getProperty("user.dir")+"/data/grade_appeal";
        File file = new File(path);
        File[] tempList = file.listFiles();
        //�ҵ����к͸�ѧԱ�йصĿγ�
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                // ����Ͳ��ݹ��ˣ�
                String fileName = tempList[i].getName();
                String[] flag = fileName.split("_");
                if (gradeMap.containsKey(flag[0])&&id.equals(flag[1])) {
                    try{
                        BufferedReader br = new BufferedReader(new FileReader(tempList[i].toString()+"\\appeal.txt"));
                        String s = null;
                        String result = "";
                        while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
                            result= result+s+"\n";
                            }
                            br.close();
                            appealMap.put(flag[0],result);
                        }catch (Exception e) {
                        e.printStackTrace();
                    }
                    }
                }
            }
        add(contain);
        setVisible(true);
    }


    //���߳ɼ����
    class submitAppeal extends JFrame implements ActionListener {
        //��ʼ����ǩ
        JPanel contain;
        JLabel courseid,text;
        JTextField courseidField;
        JTextArea textField;
        JButton submit;
        public submitAppeal() {
            super("�ύ�ɼ�����");
            setLocation(400, 400);
            setSize(400, 400);
            contain = new JPanel();
            contain.setLayout(null);
            courseid=new JLabel("�γ̺�:");
            text = new JLabel("����ԭ��:");
            submit = new JButton("�ύ");
            courseidField = new JTextField();
            textField = new JTextArea();
            //���ӻ�ͼ��
            courseid.setBounds(42,45, 75, 35);
            courseidField.setBounds(120, 45, 200, 35);
            text.setBounds(42,100,75,35);
            textField.setBounds(120,100,200,175);
            submit.setBounds(142,300,70,35);
            contain.add(courseid);
            contain.add(text);
            contain.add(courseidField);
            contain.add(textField);
            contain.add(submit);
            add(contain);
            submit.addActionListener(this);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==submit) {
                if (!gradeMap.containsKey(courseidField.getText())) {
                    JOptionPane.showMessageDialog(null, "���ſγ̲�����ķ�Χ�ڻ��߸��ſλ�δ�����ɼ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                }else if (appealMap.containsKey(courseidField.getText())) {
                    JOptionPane.showMessageDialog(null, "���Ѿ��ύ�����ſγ̵�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String path1 = System.getProperty("user.dir")+"/data/grade_appeal/"+courseidField.getText()+"_"+id;
                    try{
                        Path directory = Paths.get(path1);
                        Files.createDirectories(directory);
                        File file1 = new File(path1+"/appeal.txt");
                        file1.createNewFile();
                        FileWriter fw1 = new FileWriter(file1);
                        try (BufferedWriter writer = new BufferedWriter(fw1)) {
                            writer.write(textField.getText());
                            appealMap.put(courseidField.getText(),textField.getText());
                            JOptionPane.showMessageDialog(null, "����ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                            setVisible(false);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "����ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //�鿴�������
    class checkAppeal extends JFrame implements ActionListener {
        //��������
        JPanel contain;
        JLabel courseNum;
        JButton submit;
        HashMap<JButton,String> events;
        //���캯��
        public checkAppeal() {
            super("�鿴���߷���");
            setLocation(400, 400);
            setSize(400, 200);
            events=new HashMap<>();
            contain = new JPanel();
            contain.setLayout(new BorderLayout()); // ʹ�� BorderLayout���ֹ�����
            int num = appealMap.size();
            if (num==0) {
                courseNum=new JLabel("�㻹δ�ύ�����ߣ�");
                submit = new JButton("�ύ����");
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(submit);
                contain.add(courseNum,BorderLayout.NORTH);
                contain.add(buttonPanel,BorderLayout.CENTER);
                submit.addActionListener(this);
            }else{
                courseNum=new JLabel("�㻹��"+num+"������δ������");
                contain.add(courseNum,BorderLayout.NORTH);
                //���������������г�
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                for (String key:appealMap.keySet()){
                    String[] courseInfo = gradeMap.get(key);
                    JButton event = new JButton(courseInfo[1]);
                    events.put(event,key);
                    buttonPanel.add(event);
                    event.addActionListener(this);
                }
                contain.add(buttonPanel,BorderLayout.CENTER);
            }
            add(contain);
            setVisible(true);
        }
        //������ģ��
        class appealHandler extends JFrame implements ActionListener {
            //�������
            String courseId;
            JPanel contain;
            JTextArea stuText,TchText;
            JButton accept,refuse;
            //���캯��
            public appealHandler(String courseId) {
                super(gradeMap.get(courseId)[1]+"�ɼ�����ҳ��");
                setLocation(400, 400);
                setSize(400, 400);
                this.courseId=courseId;

                contain = new JPanel();
                contain.setLayout(new BorderLayout());
                stuText=new JTextArea();
                TchText=new JTextArea();
                stuText.setEditable(false);
                TchText.setEditable(false);
                stuText.append("�������ݣ�\n");
                stuText.append(appealMap.get(courseId));
                TchText.append("��ʦ�ظ�:\n");
                JScrollPane stu = new JScrollPane(stuText);
                JScrollPane Tch = new JScrollPane(TchText);
                JPanel TextPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                // ����Լ���Ծ�������
                gbc.fill = GridBagConstraints.BOTH; // �ð�ť��������
                gbc.weightx = 1.0; // ˮƽ����Ȩ������Ϊ1�����ֿռ�
                // ��ӵ�һ����ť
                gbc.gridx = 0; // ��0��
                gbc.gridy = 0; // ��0��
                TextPanel.add(stu, gbc);
                // ��ӵڶ�����ť
                gbc.gridx = 1; // ��1��
                TextPanel.add(Tch, gbc);
                contain.add(TextPanel,BorderLayout.CENTER);

                //��ȡ��ʦ�ظ���Ϣ
                try{
                    String path = System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id+"/reply.txt";
                    BufferedReader br = new BufferedReader(new FileReader(path));
                    String s = null;
                    String result = "";
                    accept =new JButton("���գ���������");
                    refuse = new JButton("�ܾ��������������");
                    while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
                        result= result+s+"\n";
                    }
                    TchText.append(result);
                    br.close();
                }catch (FileNotFoundException e){
                    String text = "��ʦ��δ�Ա������߽��з����������ĵȺ�";
                    TchText.append(text);
                    accept = new JButton("��������");
                    refuse = new JButton("��������");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                accept.addActionListener(this);
                refuse.addActionListener(this);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(accept);
                buttonPanel.add(refuse);
                contain.add(buttonPanel,BorderLayout.SOUTH);

                add(contain);
                setVisible(true);
            }

            //��д�������
            class rewrite extends JFrame implements ActionListener {
                JPanel contain;
                JLabel text;
                JTextArea textt;
                JButton ok;

                public rewrite() {
                    super("�ɼ�����");
                    setSize(300, 340);
                    setLocation(600, 400);
                    contain = new JPanel(new BorderLayout());
                    text = new JLabel("��������");
                    ok = new JButton("�ύ");
                    ok.addActionListener(this);
                    textt =new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(textt);
                    contain.add(text,BorderLayout.NORTH);
                    contain.add(scrollPane, BorderLayout.CENTER);
                    contain.add(ok,BorderLayout.SOUTH);
                    add(contain);
                    setVisible(true);
                }
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource()==ok) {
                        String path =System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id+"/appeal.txt";
                        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, false))) { // false ��ʾ����
                            bufferedWriter.write(textt.getText());
                            JOptionPane.showMessageDialog(null, "����ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException x) {
                            JOptionPane.showMessageDialog(null, "����ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                        }
                        setVisible(false);
                    }
                }
            }


            //������ť
            public void actionPerformed(ActionEvent e){
                if (e.getSource()==accept){
                    String path =System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id;
                    File directory = new File(path);
                    if (!directory.exists()){
                        JOptionPane.showMessageDialog(null, "�����ѳ��أ������ظ����أ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        File file1 = new File(directory,"appeal.txt");
                        if (file1.exists()){
                            file1.delete();
                        }
                        File file2 = new File(directory,"reply.txt");
                        if (file2.exists()){
                            file2.delete();
                        }
                        if (directory.delete()){
                            JOptionPane.showMessageDialog(null, "���سɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                            appealMap.remove(courseId);
                            setVisible(false);
                        }
                    }
                }else if (e.getSource()==refuse){
                    setVisible(false);
                    new rewrite();
                }
            }
        }

        //ʵ�ֽӿ�
        public void actionPerformed(ActionEvent e){
            if (e.getSource()==submit) {
                new submitAppeal();
                setVisible(false);
            }else if (events.containsKey(e.getSource())){
                String courseId = events.get(e.getSource());
                new appealHandler(courseId);
                setVisible(false);
            }
        }
    }

    //��ť�����¼�
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==appeal) {
            new submitAppeal();
        }else if(e.getSource()==check) {
            new checkAppeal();
        }
    }

}
