package �⸻����;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// �̵�� ���� ������ ó���ϴ� �߻� Ŭ����
// �̹���, ���� �� �̵�� ������ ó���� �� �������� ��ҵ��� �����ϰ� ����
public abstract class AbstractMediaQuestion extends Question {
    // �̵�� ���ϵ��� ������ �ʵ�
    protected final File file1;
    protected final File file2;
    protected final String type1;
    protected final String type2;

    // ������: ���� �ؽ�Ʈ�� �� ���� �̵�� ����, Ÿ���� ����
    public AbstractMediaQuestion(String text, File file1, File file2, String type1, String type2) {
        super(text);  // �θ� Ŭ���� Question�� ������ ȣ��
        this.file1 = file1;
        this.file2 = file2;
        this.type1 = type1;
        this.type2 = type2;

        // ���� ��ȿ�� �˻�
        validateFiles(file1, file2);
    }

    // ���� ��ȿ�� �˻� �޼���: ������ �����ϰ� ��ȿ���� Ȯ��
    private void validateFiles(File... files) {
        for (File file : files) {
            if (!file.exists() || !file.isFile()) {  // ������ �������� �ʰų� ������ �ƴϸ� ���� ó��
                throw new IllegalArgumentException("��ȿ���� ���� ����: " + file.getPath());
            }
        }
    }

    // ���� ��ư ���� �޼���
    // ��ư�� �����ϰ� �����ϴ� �޼����, ũ��, ��� ��, ������ �Ű������� �޾� ��ư�� ��ȯ
    protected JButton createButton(String text, Dimension size, Color bgColor, Runnable action) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);  // ��ư ũ�� ����
        button.setBackground(bgColor);  // ��ư ��� �� ����
        button.addActionListener(e -> action.run());  // ��ư Ŭ�� �� ������ �׼� ����
        return button;  // ������ ��ư ��ȯ
    }

    // ���� ���̾ƿ� ���� �޼���
    // �� �̵�� ������ ���� �⺻ �г� ���̾ƿ��� ����
    protected JPanel createBasePanel(String questionText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 255));  // �г� ��� �� ����

        // ���� �ؽ�Ʈ�� JLabel�� ǥ��
        JLabel questionLabel = new JLabel("<html><h2>" + questionText + "</h2></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // �۲� ����
        panel.add(questionLabel, BorderLayout.NORTH);  // �г��� ��ܿ� ���� ���̺� �߰�

        return panel;  // ������ �г� ��ȯ
    }
}
