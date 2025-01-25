package �⸻����;
import javax.swing.*;
import javazoom.jl.player.Player;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
// ���� Ŭ����: ���α׷� ������
public class MBTItest {
	  static Player backgroundMusicPlayer = null;
	    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartScreen().setVisible(true));
    }

 // ������� ���
    public static void playBackgroundMusic(String musicFile) {
    	 // ��������� �̹� ��� ���� ��� ���߱�
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.close();
        }
        new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(musicFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                Player player = new Player(bis); // JLayer Player
                player.play(); // ���� ���
            } catch (Exception e) {
                System.err.println("������� ��� ����: " + e.getMessage());
            }
        }).start();
    }

    	}


//�⺻ ���� Ŭ���� BasicQuestion
class BasicQuestion extends Question {
private final String option1;
private final String option2;
private final String type1;
private final String type2;

public BasicQuestion(String text, String option1, String option2, String type1, String type2) {
   super(text);
   this.option1 = option1;
   this.option2 = option2;
   this.type1 = type1;
   this.type2 = type2;
}

@Override
public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
    // �г� ���� �� ���̾ƿ� ����
    JPanel panel = new JPanel(new BorderLayout());

    // ���� �� �߰�
    JLabel questionLabel = new JLabel("<html><h2>" + getText() + "</h2></html>", SwingConstants.CENTER);
    questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // �۲� �� ũ�� ����
    panel.add(questionLabel, BorderLayout.NORTH);

 // ����ȭ�� �̹��� �߰� �κ�
    JLabel startLabel = new JLabel();
    startLabel.setHorizontalAlignment(JLabel.CENTER); // ���� ����
    startLabel.setVerticalAlignment(JLabel.CENTER); // ���� ����
    panel.add(startLabel, BorderLayout.CENTER);

    // �г� ����
    panel.setVisible(true);
    JButton button1 = new JButton(option1);
    JButton button2 = new JButton(option2);


    // ��ư ũ�� �� ��Ÿ�� ����
    Dimension buttonSize = new Dimension(150, 50);
    button1.setPreferredSize(buttonSize);
    button2.setPreferredSize(buttonSize);
    button1.setFont(new Font("SansSerif", Font.BOLD, 15));
    button2.setFont(new Font("SansSerif", Font.BOLD, 15));
    button1.setBackground(new Color(0,128,0)); // �ʷ�
    button2.setBackground(new Color(0,128,0));
    button1.setPreferredSize(new Dimension(300,100)); 
    button2.setPreferredSize(new Dimension(300,100));    
    button1.setForeground(Color.WHITE);
    button2.setForeground(Color.WHITE);
    button1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    button2.setBorder(BorderFactory.createLineBorder(Color.BLACK));



    button1.addActionListener(e -> {
        scores.put(type1, scores.get(type1) + 1);
        onNext.run();
    });
    button2.addActionListener(e -> {
        scores.put(type2, scores.get(type2) + 1);
        onNext.run();
    });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setOpaque(false);
    buttonPanel.add(button1);
    buttonPanel.add(button2);

    panel.add(buttonPanel, BorderLayout.CENTER);
    return panel;
}

	}


// ���� ȭ�� Ŭ����: �г��� �Է�
class StartScreen extends JFrame {

	public StartScreen() {
        setTitle("���� MBTI TEST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("���� MBTI TEST", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        panel.add(title, BorderLayout.NORTH);
        panel.setBackground(new Color(70,130,180)); // ���� �ϴû� ���
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("�г����� �Է��ϼ���: ", SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        inputPanel.add(nameLabel, BorderLayout.SOUTH);

        JTextField nameField = new JTextField(20);
        JButton startButton = new JButton("����");

        startButton.addActionListener(e -> {
            String nickname = nameField.getText().trim();
            if (!nickname.isEmpty()) {
                dispose(); // ���� ȭ�� �ݱ�
                new MBTITestFrame(nickname).setVisible(true); // �׽�Ʈ ȭ�� ����
            } else {
                JOptionPane.showMessageDialog(this, "�г����� �Է����ּ���!", "����", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startButton);
        panel.add(inputPanel, BorderLayout.CENTER);
        add(panel);
        
    	playBackgroundMusic("�������.mp3");

    }

	private void playBackgroundMusic(String musicFile) {
        new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(musicFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                Player player = new Player(bis); // JLayer Player
                player.play(); // ���� ���
            } catch (Exception e) {
                System.err.println("������� ��� ����: " + e.getMessage());
            }
        }).start();


}

// MBTI �׽�Ʈ ������
class MBTITestFrame extends JFrame {
   

	private final String nickname;
    private final CardLayout cardLayout = new CardLayout(); // ī�� ���̾ƿ����� ���� ȭ�� ��ȯ
    private final JPanel mainPanel = new JPanel(cardLayout); // ���� �г�
    private final Map<String, Integer> scores = new HashMap<>(); // MBTI ���� ����
    private int currentQuestion = 0; // ���� ���� ��ȣ

    // ���� ����Ʈ: �⺻ ������ �̵�� ���� ����
    private final Question[] questions = {
    		
    		//E vs I
    		new BasicQuestion("1. �������� ���� ������ ���ѷ� �ϴµ�, ���� ���� �����͵� ���� ���� �ʴ´�.", "������ ������.. ���� ��� ��ٸ���.", "���� �Ҹ�ģ��. �����~!!!", "I", "E"),
    		//N vs S
            new BasicQuestion("2. ���� ���ϴ� �����?", "�ʳ� ������ ������ �� �� ���� ���", "���� ���� ������ �κ��� �޲� �� �ִ� ���", "N", "S"),
    		//F vs T
            new BasicQuestion("3. '�� ���� ���˻�� ����!' ��� ģ���� ��ȭ�� �ް� ����", "���� ����ڴ�... ��ģ ���� ����?", "����? ����ó���� �߾�?", "F", "T"),
    		//J vs P
            new BasicQuestion("4. ������ �޾Ҵ�. ����", "������ ���� ��ȹ�� ������ Ʋ�� ������.", "�ڷ�������� �� ���� Ʋ�� ������.", "J", "P"),
    		//E vs I
            new ImageQuestion("5. �� �� ���� ���̰� ���� ��Ȳ��?", new File("��Ƽ�̹���.jpg"), new File("�����̹���.png"), "E", "I"),
    		//N vs S            
            new ImageQuestion("6. �� �� �� ������ �� ��� �׸���?", new File("���ȭ.png"), new File("����ȭ.jpg"), "N", "S"),
    		//F vs T
            new ImageQuestion("7. '�� ����...'��� ������ �� '������ ��'��� ���� ��� �޾Ƶ��̳���?", new File("F©.jpg"), new File("T©.jpg"), "F", "T"),
    		//J vs P
            new ImageQuestion("8. ���� ������ ����� �ߴ�. �Ϸ� �� ���� �����?", new File("J©.png"), new File("P©.png"), "J", "P"),
            //E vs I
            new MediaQuestion("9. �� ������ ���� ��� ��� ��ܵ�� ��Ÿ���� �뷡�� ������.", new File("E����.mp3"), new File("I����.mp3"), "E", "I"),
    		//N vs S
            new MediaQuestion("10. �� ������ ���� ��� ���簡 �� ������ ��� ������ ������.", new File("N����.mp3"), new File("S����.mp3"), "N", "S"),
            //F vs T
            new BasicQuestion("11. '�ٵ� ���� �ʶ� ������ ��¥ �� �´� �� ����.' ��� ���� ��� ����?", "(��ó)���� �ݴ뼺���� ������� �� �¾�.. ","�׷� �� ����! ����� �� �ٸ��ϱ�.", "F", "T"),
    		//J vs P
            new TextQuestion("12. ���� �Ϸ�, ����� ��ȹ�� �ۼ��غ�����.")
    };

    // ������
    public MBTITestFrame(String nickname) {
        this.nickname = nickname;
        setTitle("���� MBTI TEST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        // ���� �ʱ�ȭ
        scores.put("E", 0);
        scores.put("I", 0);
        scores.put("N", 0);
        scores.put("S", 0);
        scores.put("F", 0);
        scores.put("T", 0);
        scores.put("J", 0);
        scores.put("P", 0);

     // ���� �г� �߰�

        for (Question question : questions) {
            JPanel questionPanel = question.getPanel(() -> {
                if (currentQuestion < questions.length - 1) {
                    currentQuestion++;
                    cardLayout.next(mainPanel);
                } else {
                    displayResults();
                }
            }, scores);
            
            // ��ư ũ�⸦ 100x100���� ���� ����
            for (Component component : questionPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setPreferredSize(new Dimension(100,100)); // ��ư ũ�� ����
                    button.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                }
            }

            mainPanel.add(questionPanel, question.getText());
        }


        add(mainPanel);
        mainPanel.setBackground(new Color(70,130,180)); // ���� �ϴû� ���

        setFontAndColor(mainPanel);

    }
    // �۲� �� ��ư ��Ÿ�� ����
    private void setFontAndColor(JComponent component) {
        Font font = new Font("SansSerif", Font.BOLD, 15); // ������ ���� �۾�ü �� ũ��
        for (Component child : component.getComponents()) {
            if (child instanceof JButton) {
                JButton button = (JButton) child;
                button.setFont(font);
                button.setBackground(new Color(0,128,128)); //��ư����:û�ϻ�
                button.setForeground(Color.white); // ��ư�۾���: �Ͼ��
                button.setFocusPainted(false);
            } else if (child instanceof JLabel) {
                ((JLabel) child).setFont(font);
            } else if (child instanceof JPanel) {
                setFontAndColor((JComponent) child);
            }
        }
    }


    // ����� ����ϰ� ǥ��
    private void displayResults() {
        StringBuilder resultText = new StringBuilder("�׽�Ʈ ���:\n");

        // MBTI ���� ���
        StringBuilder mbti = new StringBuilder();
        mbti.append(scores.get("E") > scores.get("I") ? "E" : "I");
        mbti.append(scores.get("N") > scores.get("S") ? "N" : "S");
        mbti.append(scores.get("F") > scores.get("T") ? "F" : "T");
        mbti.append(scores.get("J") > scores.get("P") ? "J" : "P");

        resultText.append(nickname).append("���� MBTI��: ").append(mbti).append("\n");

        // ����� ���Ͽ� ����
        try (FileWriter writer = new FileWriter("test_results.txt")) {
            writer.write(resultText.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "����� ���Ͽ� �����ϴ� �� ������ �߻��߽��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
        }

        // MBTI�� ���� ���� �б�
        String description = "���� ������ �ҷ����� �� ���� �߻�"; // �⺻��
        try {
            description = Files.readString(Paths.get(mbti + ".txt"));
        } catch (IOException e) {
            description = "������ �ҷ����� �� �����߽��ϴ�.";
        }

     // ��� ȭ�� ����
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel1 = new JLabel("<html><h2 style='font-size: 25px;'>[ �׽�Ʈ ��� ]</h2></html>", SwingConstants.CENTER);
        resultPanel.add(resultLabel1, BorderLayout.NORTH);
        resultLabel1.setFont(new Font("SansSerif", Font.BOLD, 40));

        JLabel resultLabel2 = new JLabel("<html><h2 style='font-size: 20px;'>" + nickname + "���� MBTI: " + mbti + "</h2 style='font-size: 15px;><p>" + description + "</p></html>");
        resultLabel2.setHorizontalAlignment(SwingConstants.CENTER); // ���� ����
        resultPanel.add(resultLabel2, BorderLayout.CENTER);

        // MBTI�� ĳ���� �̹��� �߰�
        JLabel characterImageLabel = new JLabel(new ImageIcon(mbti + ".png")); // MBTI�� ���� ĳ���� �̹���
        resultPanel.add(characterImageLabel, BorderLayout.SOUTH);

        // ������ ȭ�� ��ȯ
        mainPanel.add(resultPanel, "���");
        cardLayout.show(mainPanel, "���");
    }
}
} 