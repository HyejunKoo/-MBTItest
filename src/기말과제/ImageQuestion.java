package �⸻����;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

// �̹��� ������ ó���ϴ� Ŭ���� ImageQuestion
// �̹��� 2���� �����ϴ� ������ �����ϴ� Ŭ����
public class ImageQuestion extends AbstractMediaQuestion {

    // ������: �ؽ�Ʈ, �̹��� ���� 2��, ���� �׸� 2���� �ʱ�ȭ
    public ImageQuestion(String text, File image1, File image2, String type1, String type2) {
        super(text, image1, image2, type1, type2);  // �θ� Ŭ������ AbstractMediaQuestion�� ������ ȣ��
    }

    @Override
    // �г��� �����ϰ�, �̹����� �����ִ� GUI ������Ʈ�� ��ȯ�ϴ� �޼���
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        // �⺻���� ���� �ؽ�Ʈ�� �Բ� �г� ����
        JPanel panel = createBasePanel(getText());

        // �̹����� ǥ���� �г��� �����ϰ�, 1�� 2���� GridLayout���� ��ġ
        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        imagePanel.setOpaque(false);  // ����� �����ϰ� ����

        // �̹����� ũ�� �����Ͽ� JLabel�� ǥ��
        JLabel imageLabel1 = createScaledImageLabel(file1);
        JLabel imageLabel2 = createScaledImageLabel(file2);

        // ù ��° �̹��� Ŭ�� �� ���� ����
        imageLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scores.put(type1, scores.get(type1) + 1);  // �ش� Ÿ���� ���� +1
                onNext.run();  // ���� �������� �Ѿ
            }
        });

        // �� ��° �̹��� Ŭ�� �� ���� ����
        imageLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scores.put(type2, scores.get(type2) + 1);  // �ش� Ÿ���� ���� +1
                onNext.run();  // ���� �������� �Ѿ
            }
        });

        // �̹����� �гο� �߰�
        imagePanel.add(imageLabel1);
        imagePanel.add(imageLabel2);

        // �̹��� �г��� ��ü �гο� �߰�
        panel.add(imagePanel, BorderLayout.CENTER);

        return panel;  // �г� ��ȯ
    }

    // �̹����� ũ�� �����Ͽ� JLabel�� ��ȯ�ϴ� �޼���
    private JLabel createScaledImageLabel(File imageFile) {
        ImageIcon originalIcon = new ImageIcon(imageFile.getPath());  // ���Ͽ��� �̹��� �ε�
        Image originalImage = originalIcon.getImage();  // �̹��� ��ü ����
        Image scaledImage = originalImage.getScaledInstance(300, 350, Image.SCALE_SMOOTH);  // ũ�� ����
        return new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);  // ũ�� ������ �̹����� JLabel�� ��ȯ
    }
}
