package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GradeAppeal extends JFrame implements ActionListener {
    //����ɼ���������
    JPanel contain;
    JButton check,appeal;
    //����map���ڼ��
    HashMap gradeMap,appealMap;


    //����ɼ��������
    public GradeAppeal(HashMap gradeMap) {
        super("�ɼ�����");
        setLocation(300, 200);
        setSize(300, 300);
        this.gradeMap = gradeMap;
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

        add(contain);
        setVisible(true);
    }

    //���߳ɼ����
    void submitAppeal() {}

    //�鿴�������
    void checkAppeal() {}

    //��ť�����¼�
    public void actionPerformed(ActionEvent e) {}

}
