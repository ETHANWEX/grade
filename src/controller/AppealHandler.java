package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AppealHandler extends JFrame implements ActionListener {
    //���Ԫ��
    JPanel contain;
    int courseNum,stuNum;
    HashMap<JButton,String> events;
    HashMap<String, ArrayList<String>> map;
    String tid;
    HashMap<String,String> courseName;

    //���캯��
    public AppealHandler(String tid) {
        // �������
        super("�鿴���߷���");
        setLocation(400, 400);
        setSize(400, 200);
        events=new HashMap<>();
        contain = new JPanel();
        contain.setLayout(new BorderLayout()); // ʹ�� BorderLayout���ֹ�����
        this.tid=tid;
        map=new HashMap<>();
        courseName=hasThisCourse();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // ��ѯ����ʦ�����ڿγ̵�����
        String path = System.getProperty("user.dir")+"/data/grade_appeal";
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                // ����Ͳ��ݹ��ˣ�
                String fileName = tempList[i].getName();
                String[] flag = fileName.split("_");
                String courseId = flag[0];
                String stuId = flag[1];
                if (courseName.containsKey(courseId)) {
                    File f = new File(tempList[i].getPath()+"/reply.txt");
                    if (!f.exists()) {
                        //���γ�ѧ�������췽���������
                        if (map.containsKey(courseId)) {
                            map.get(courseId).add(stuId);
                        }else{
                            ArrayList<String> list = new ArrayList<>();
                            list.add(stuId);
                            map.put(courseId,list);
                        }
                        stuNum++;
                    }
                }
            }
        }

        courseNum =map.size();
        if (stuNum==0){
            buttonPanel.add(new JLabel("�㲢δ�ܵ��й�����"));
        }else{
            contain.add(new JLabel("�㻹��" +courseNum+"����ؿγ�,��"+stuNum+"������δ�ظ�"),BorderLayout.NORTH);
            for(String key:map.keySet()){
                JButton event = new JButton(courseName.get(key));
                events.put(event,key);
                buttonPanel.add(event);
                event.addActionListener(this);
            }
        }
        contain.add(buttonPanel,BorderLayout.CENTER);
        add(contain);
        setVisible(true);
    }

    HashMap<String,String> hasThisCourse() {

        String file = System.getProperty("user.dir")+"/data/course.txt";
        HashMap<String,String> map = new HashMap<>();
        // String file = "D://test//course.txt";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                String[] result = s.split(" ");
                if(result[4].equals(tid)){
                    map.put(result[0],result[1]);
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }


    //�ӿ�ʵ��
    public void actionPerformed(ActionEvent e) {
        if (events.containsKey(e.getSource())) {
            new stuTree(events.get(e.getSource()));
            setVisible(false);
        }
    }

    class stuTree extends JFrame implements ActionListener {
        JPanel contain;
        HashMap<JButton,String> events;
        String cId;

        public stuTree(String cId) {
            super(courseName.get(cId)+"����ҳ��");
            this.cId=cId;
            setLocation(400, 400);
            setSize(400, 200);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            events=new HashMap<>();
            contain = new JPanel();
            contain.setLayout(new BorderLayout()); // ʹ�� BorderLayout���ֹ�����
            ArrayList<String> list = map.get(cId);
            int nums= list.size();
            contain.add(new JLabel(courseName.get(cId)+"�л���"+nums+"������δ�ظ�"),BorderLayout.NORTH);
            for(String key:list){
                JButton event = new JButton(key);
                events.put(event,key);
                event.addActionListener(this);
                buttonPanel.add(event);
            }
            contain.add(buttonPanel,BorderLayout.CENTER);
            add(contain);
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (events.containsKey(e.getSource())) {
                new handler(cId,events.get(e.getSource()));
                setVisible(false);
            }
        }
    }

    class handler extends JFrame implements ActionListener {
        JPanel contain;
        JTextArea stuText;
        JButton accept,refuse;
        String cId,sId;


        public handler(String cId, String sId) {
            super(courseName.get(cId)+"��"+sId+"����ҳ��");
            setLocation(400, 400);
            setSize(400, 400);
            contain = new JPanel();
            contain.setLayout(new BorderLayout());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            stuText=new JTextArea();
            stuText.setEditable(false);
            stuText.append("�������ݣ�\n");
            JScrollPane stu = new JScrollPane(stuText);
            contain.add(stu,BorderLayout.CENTER);
            accept = new JButton("ͬ�����߲����ĳɼ� ");
            refuse = new JButton("�ܾ�������ԭ��");
            accept.addActionListener(this);
            refuse.addActionListener(this);
            buttonPanel.add(accept);
            buttonPanel.add(refuse);
            contain.add(buttonPanel,BorderLayout.SOUTH);
            this.cId=cId;
            this.sId=sId;
            //��ȡѧ����������
            try{
                String path = System.getProperty("user.dir")+"/data/grade_appeal/"+cId+"_"+sId+"/appeal.txt";
                BufferedReader br = new BufferedReader(new FileReader(path));
                String s = null;
                while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
                    stuText.append(s+"\n");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            add(contain);
            setVisible(true);
        }

        //�����ύ
        class reply extends JFrame implements ActionListener {
            JPanel contain;
            JLabel grade,reason;
            JTextField gradeUpdate;
            JTextArea text;
            JButton submit;
            int flag;
            public reply(int flag){
                super("�ظ�����");
                setLocation(400, 400);
                setSize(400, 400);
                this.flag=flag;
                contain = new JPanel();
                contain.setLayout(null);
                submit = new JButton("�ύ");
                reason = new JLabel("ԭ��");
                text = new JTextArea();
                reason.setBounds(42,100,75,35);
                text.setBounds(120,100,200,175);
                submit.setBounds(142,300,70,35);
                contain.add(reason);
                contain.add(text);
                contain.add(submit);
                //����Ǿܾ�����ͬ��
                if (flag==0){
                    grade=new JLabel("���ĳɼ�Ϊ:");
                    gradeUpdate = new JTextField();
                    grade.setBounds(42,45, 75, 35);
                    gradeUpdate.setBounds(120, 45, 200, 35);
                    contain.add(grade);
                    contain.add(gradeUpdate);
                }
                contain.add(submit);
                add(contain);
                submit.addActionListener(this);
                setVisible(true);
            }

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submit) {
                    String reply = "";
                    String temp ="";
                    String tempPath="";
                    if (flag==0){
                        int number = Integer.parseInt(gradeUpdate.getText());
                        if ( number >100 || number <0){
                            JOptionPane.showMessageDialog(null, "����ɼ�����Ӧ��Ϊ0-100֮�������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }else{
                            // �Ҷ�Ӧ�γ̳ɼ��ļ�
                            // ��ѯ����ʦ�����ڿγ̵�����
                            String path1 = System.getProperty("user.dir")+"/data/grade";
                            File file = new File(path1);
                            File[] tempList = file.listFiles();

                            //�޸��ļ��гɼ�
                            for (int i = 0; i < tempList.length; i++) {
                                if (tempList[i].isFile()) {
                                    try{
                                        //��ȡ�ļ�����
                                        BufferedReader br = new BufferedReader(new FileReader(tempList[i].getPath()));
                                        String s = br.readLine();
                                        String[] result = s.split(" ");
                                        if(!result[0].equals(cId)){
                                            continue;
                                        }
                                        if(result[4].equals(sId)){
                                            result[6]=gradeUpdate.getText();
                                        }
                                        tempPath=tempList[i].getPath();
                                        temp+=String.join(" ", Arrays.asList(result))+"\n";
                                        while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                                            result = s.split(" ");
                                            if(result[4].equals(sId)){
                                                result[6]=gradeUpdate.getText();
                                            }
                                            temp+=String.join(" ", Arrays.asList(result))+"\n";
                                        }
                                        br.close();
                                        //д��������
                                    }catch(Exception x){
                                        x.printStackTrace();
                                    }
                                }
                            }
                            reply+="��ʦ������ĳɼ�Ϊ"+gradeUpdate.getText()+"\n";
                        }
                    }
                    reply+=text.getText();
                    String path =System.getProperty("user.dir")+"/data/grade_appeal/"+cId+"_"+sId+"/reply.txt";
                    try {
                        //д���³ɼ�
                        if (flag==0) {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempPath, false));
                            bufferedWriter.write(temp);
                            bufferedWriter.close();
                        }
                        File file1 = new File(path);
                        file1.createNewFile();
                        FileWriter fw1 = new FileWriter(file1);
                        BufferedWriter bw = new BufferedWriter(fw1);
                        bw.write(reply);
                        bw.close();
                        JOptionPane.showMessageDialog(null, "����ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException x) {
                        System.out.println(cId+sId);
                        JOptionPane.showMessageDialog(null, "����ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                    }
                    setVisible(false);
                }
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == accept) {
                new reply(0);
            }else if (e.getSource() == refuse) {
                new reply(1);
            }
            setVisible(false);
        }
    }


}
