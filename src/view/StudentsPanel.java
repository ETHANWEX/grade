package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CourseView;
import controller.EditInfo;
import controller.GradeInfo;
import controller.Info;



@SuppressWarnings("serial")
public class StudentsPanel extends JFrame implements ActionListener {
	/*
	 * ѧ����½�����������
	 */
	JPanel contain;
	String id;
	JButton infoButton, gradeButton, courseButton, editButton,backButton;

	public StudentsPanel(String id) {
		super("ѧ��");
		this.id = id;
		setLocation(300, 200);
		setSize(300, 380);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("��Ϣ��ѯ");
		gradeButton = new JButton("�ɼ���ѯ");
		courseButton = new JButton("�γ̲�ѯ");
		editButton = new JButton("�޸���Ϣ");
		backButton = new JButton("���ص�¼");
		infoButton.setBounds(70, 40, 140, 30);
		gradeButton.setBounds(70, 80, 140, 30);
		courseButton.setBounds(70, 120, 140, 30);
		editButton.setBounds(70, 160, 140, 30);
		backButton.setBounds(70, 200, 140, 30); // ���÷��ذ�ť��λ��
		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(backButton); // ��ӷ��ذ�ť
		backButton.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		//��������ѡ��
		if (e.getSource() == infoButton) {
			//��ѯ��Ϣ
			new Info(id, 1);
		}
		if (e.getSource() == gradeButton) {
			//��ѯ�ɼ�
			new GradeInfo(id);
		}
		if (e.getSource() == courseButton) {
			//�鿴�γ�
			new CourseView(id, 0);
		}
		if (e.getSource() == editButton) {
			//�޸���Ϣ
			new EditInfo(id, 0);
		}
		if (e.getSource() == backButton) {
			this.dispose();  // ���ٵ�ǰѧ����崰��
			new MainFrame();  // ���ص���¼����
		}
	}
	//�رմ���
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
