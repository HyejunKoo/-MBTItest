package �⸻����;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// �ؽ�Ʈ �Է� ������ ó���ϴ� Ŭ���� TextQuestion
// ����ڰ� �ؽ�Ʈ�� �Է��ϰ�, 100�� �̻����� Ȯ���Ͽ� ������ ������Ʈ�ϴ� ������ �����ϴ� Ŭ����
public class TextQuestion extends Question {
    private JTextArea inputTextArea;  // �ؽ�Ʈ �Է��� ���� JTextArea
    private JButton submitButton;    // ���� ��ư

    // ������: ���� �ؽ�Ʈ�� �޾Ƽ� �θ� Ŭ���� Question�� �����ڸ� ȣ��
    public TextQuestion(String questionText) {
        super(questionText);  // �θ� Ŭ������ ������ ȣ��
    }

    // �ؽ�Ʈ ���̸� üũ�ϰ� ������ ������Ʈ�ϴ� �޼���
    // 50�� �̻� �Է��ϸ� 'J' ���� +1, 50�� �̸� �Է��ϸ� 'P' ���� +1
    private void checkTextLength(String inputText, Map<String, Integer> scores) {
        if (inputText.length() >= 50) {
            scores.put("J", scores.get("J") + 1);  // 50�� �̻��̸� 'J' ���� +1
        } else {
            scores.put("P", scores.get("P") + 1);  // 50�� �̸��̸� 'P' ���� +1
        }
    }

    @Override
    // �ؽ�Ʈ �Է� ������ ���� �г��� �����ϰ� ��ȯ�ϴ� �޼���
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 255));  // �г� ��� �� ����

        // ���� �ؽ�Ʈ�� JLabel�� ǥ��
        JLabel questionLabel = new JLabel("<html><h2>" + getText() + "</h2></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // �۲� ����
        panel.add(questionLabel, BorderLayout.NORTH);  // ���� ���̺��� �г��� ���ʿ� �߰�

        // �ؽ�Ʈ �Է��� ���� JTextArea ����
        inputTextArea = new JTextArea(10, 30);  // 10�� 30�� ũ��
        inputTextArea.setWrapStyleWord(true);  // �ܾ �������� �ʵ��� ����
        inputTextArea.setLineWrap(true);  // �� �ٲ��� ���
        JScrollPane scrollPane = new JScrollPane(inputTextArea);  // ��ũ�� �����ϵ��� ����
        panel.add(scrollPane, BorderLayout.CENTER);  // �ؽ�Ʈ �Է� �ʵ带 �гο� �߰�

        // ���� ��ư ����
        submitButton = new JButton("����");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));  // �۲� ����
        submitButton.setBackground(new Color(135, 206, 250));  // ��ư ��� ��
        submitButton.setForeground(Color.WHITE);  // ��ư ���� ��

        // ���� ��ư Ŭ�� �� �ؽ�Ʈ ���� Ȯ�� �� ���� ������Ʈ
        submitButton.addActionListener(e -> {
            String inputText = inputTextArea.getText().trim();  // �Էµ� �ؽ�Ʈ ��������
            if (inputText.isEmpty()) {  // �ؽ�Ʈ�� ��������� ���â ���
                JOptionPane.showMessageDialog(
                    panel, 
                    "���ڸ� �Է����ּ���.",  // ��� �޽���
                    "���", 
                    JOptionPane.WARNING_MESSAGE  // ��� �޽��� �ڽ�
                );
            } else {
                checkTextLength(inputText, scores);  // �ؽ�Ʈ ���̿� ���� ���� ������Ʈ
                onNext.run();  // ���� �������� �Ѿ�� ��
            }
        });

        // ���� ��ư�� ��ġ�� �г� ����
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);  // ��ư �г��� �����ϰ� ����
        buttonPanel.add(submitButton);  // ���� ��ư�� ��ư �гο� �߰�
        panel.add(buttonPanel, BorderLayout.SOUTH);  // ��ư �г��� �г��� �ϴܿ� �߰�

        return panel;  // ������ �г� ��ȯ
    }
}
