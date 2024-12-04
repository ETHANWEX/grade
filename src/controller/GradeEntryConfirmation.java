package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GradeEntryConfirmation extends JFrame {
    private GradeEnter gradeEnter;
    private JFrame gradeInputFrame;
    private JTextField stuIdt, stuGradet, stuNamet;

    public GradeEntryConfirmation(GradeEnter gradeEnter, JFrame gradeInputFrame, JTextField stuIdt, JTextField stuGradet, JTextField stuNamet) {
        this.gradeEnter = gradeEnter;
        this.gradeInputFrame = gradeInputFrame;
        this.stuIdt = stuIdt;
        this.stuGradet = stuGradet;
        this.stuNamet = stuNamet;

        // ȷ�Ͽ����
        setSize(250, 150);
        setLocation(650, 450);
        setTitle("�ɼ�¼��");

        JPanel panel = new JPanel();
        JLabel label = new JLabel("�ɼ�¼����ɣ��Ƿ������");

        JButton continueButton = new JButton("����¼��");
        JButton finishButton = new JButton("����¼��");

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ��ս������¼��
                stuIdt.setText("");
                stuGradet.setText("");
                stuNamet.setText("");
                setVisible(false);
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // �ر�������ؽ���
                gradeInputFrame.dispose();
                gradeEnter.dispose();
                setVisible(false);
            }
        });

        panel.add(label);
        panel.add(continueButton);
        panel.add(finishButton);

        add(panel);
        setVisible(true);
    }
}
