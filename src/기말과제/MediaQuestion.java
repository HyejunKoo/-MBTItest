package �⸻����; 

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javazoom.jl.player.Player;

// �̵�� ��� ������ ó���ϴ� MediaQuestion Ŭ���� (AbstractMediaQuestion�� ���)
public class MediaQuestion extends AbstractMediaQuestion {
    private Player[] players = new Player[2];  // �� ���� ������ ������ Player ��ü �迭

    // ������: ���� �ؽ�Ʈ�� �� ���� �̵�� ����, ���� ������ �ʱ�ȭ
    public MediaQuestion(String text, File media1, File media2, String type1, String type2) {
        super(text, media1, media2, type1, type2);  // �θ� Ŭ���� AbstractMediaQuestion�� ������ ȣ��
    }

    // ������ �ش��ϴ� �г��� ��ȯ�ϴ� �޼���
    @Override
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        // �⺻ �г� ���� (���� �ؽ�Ʈ ����)
        JPanel panel = createBasePanel(getText());  // ���� �ؽ�Ʈ�� �����ϴ� �⺻ �г� ����

        // �̵�� ��ư���� ���� �г� ���� (���� ��� �� ���� ��ư)
        JPanel mediaPanel = new JPanel(new GridLayout(1, 4, 10, 10));  // �׸��� ���̾ƿ����� 1�� 4�� ��ġ
        Dimension buttonSize = new Dimension(100, 80);  // ��ư ũ�� ����

        // ���� 1�� 2�� ��� �� ���� ��ư ����
        JButton playButton1 = createButton("���� 1 ���", buttonSize, Color.CYAN, () -> playMedia(0, file1));
        JButton stopButton1 = createButton("���� 1 ����", buttonSize, Color.RED, () -> stopMedia(0));
        JButton playButton2 = createButton("���� 2 ���", buttonSize, Color.CYAN, () -> playMedia(1, file2));
        JButton stopButton2 = createButton("���� 2 ����", buttonSize, Color.RED, () -> stopMedia(1));

        // ���/���� ��ư���� mediaPanel�� �߰�
        mediaPanel.add(playButton1);
        mediaPanel.add(stopButton1);
        mediaPanel.add(playButton2);
        mediaPanel.add(stopButton2);

        // ������ �����ϴ� ��ư���� ���� �г� ����
        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));  // 1�� 2���� ���� ��ư ��ġ
        JButton chooseButton1 = createButton("���� 1 ����", buttonSize, Color.GREEN, () -> {
            scores.put(type1, scores.get(type1) + 1);  // ���� 1�� �����ϸ� ���� ������Ʈ
            endMediaQuestion(onNext);  // ���� ���� �� �ļ� �۾� ����
        });

        JButton chooseButton2 = createButton("���� 2 ����", buttonSize, Color.GREEN, () -> {
            scores.put(type2, scores.get(type2) + 1);  // ���� 2�� �����ϸ� ���� ������Ʈ
            endMediaQuestion(onNext);  // ���� ���� �� �ļ� �۾� ����
        });

        // ���� ��ư���� choicePanel�� �߰�
        choicePanel.add(chooseButton1);
        choicePanel.add(chooseButton2);

        // �гο� �̵�� ��ư�� ���� ��ư �г��� �߰�
        panel.add(mediaPanel, BorderLayout.CENTER);
        panel.add(choicePanel, BorderLayout.SOUTH);

        return panel;  // ���� �г� ��ȯ
    }

    // ���� ��� �޼���: �־��� �ε����� ���Ϸ� ������ ���
    private void playMedia(int index, File mediaFile) {
        stopMedia(index);  // ���� ��� ���� ������ ���߰� ���ο� ���� ���
        try {
            // FileInputStream�� ���� ������ �а�, Player ��ü�� ������ ���
            FileInputStream fis = new FileInputStream(mediaFile);
            players[index] = new Player(fis);  // �ش� �ε����� Player ��ü ����
            new Thread(() -> {
                try {
                    players[index].play();  // ���� ������ Player�� ���� ���
                } catch (Exception ex) {
                    ex.printStackTrace();  // ���� ó��
                }
            }).start();  // ������ �����忡�� ������ ���
        } catch (Exception ex) {
            ex.printStackTrace();  // ���� ó��
        }
    }

    // ���� ���� �޼���: �־��� �ε����� �ش��ϴ� ������ ����
    private void stopMedia(int index) {
        if (players[index] != null) {
            players[index].close();  // ���� ����
            players[index] = null;  // Player ��ü�� null�� ����
        }
    }

    // ���� ���� �� �ļ� ó���� ���� �޼���
    private void endMediaQuestion(Runnable onNext) {
        // ��� ������ ������Ű��, �ļ� �۾� ����
        for (int i = 0; i < players.length; i++) {
            stopMedia(i);
        }
        onNext.run();  // �ļ� �۾� ���� (���� �������� �Ѿ��)
    }
}