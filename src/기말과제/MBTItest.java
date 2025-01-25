package 기말과제;
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
// 메인 클래스: 프로그램 시작점
public class MBTItest {
	  static Player backgroundMusicPlayer = null;
	    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartScreen().setVisible(true));
    }

 // 배경음악 재생
    public static void playBackgroundMusic(String musicFile) {
    	 // 배경음악이 이미 재생 중일 경우 멈추기
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.close();
        }
        new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(musicFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                Player player = new Player(bis); // JLayer Player
                player.play(); // 음악 재생
            } catch (Exception e) {
                System.err.println("배경음악 재생 오류: " + e.getMessage());
            }
        }).start();
    }

    	}


//기본 질문 클래스 BasicQuestion
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
    // 패널 생성 및 레이아웃 설정
    JPanel panel = new JPanel(new BorderLayout());

    // 질문 라벨 추가
    JLabel questionLabel = new JLabel("<html><h2>" + getText() + "</h2></html>", SwingConstants.CENTER);
    questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // 글꼴 및 크기 설정
    panel.add(questionLabel, BorderLayout.NORTH);

 // 시작화면 이미지 추가 부분
    JLabel startLabel = new JLabel();
    startLabel.setHorizontalAlignment(JLabel.CENTER); // 수평 정렬
    startLabel.setVerticalAlignment(JLabel.CENTER); // 수직 정렬
    panel.add(startLabel, BorderLayout.CENTER);

    // 패널 설정
    panel.setVisible(true);
    JButton button1 = new JButton(option1);
    JButton button2 = new JButton(option2);


    // 버튼 크기 및 스타일 설정
    Dimension buttonSize = new Dimension(150, 50);
    button1.setPreferredSize(buttonSize);
    button2.setPreferredSize(buttonSize);
    button1.setFont(new Font("SansSerif", Font.BOLD, 15));
    button2.setFont(new Font("SansSerif", Font.BOLD, 15));
    button1.setBackground(new Color(0,128,0)); // 초록
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


// 시작 화면 클래스: 닉네임 입력
class StartScreen extends JFrame {

	public StartScreen() {
        setTitle("감각 MBTI TEST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("감각 MBTI TEST", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        panel.add(title, BorderLayout.NORTH);
        panel.setBackground(new Color(70,130,180)); // 진한 하늘색 배경
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("닉네임을 입력하세요: ", SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        inputPanel.add(nameLabel, BorderLayout.SOUTH);

        JTextField nameField = new JTextField(20);
        JButton startButton = new JButton("시작");

        startButton.addActionListener(e -> {
            String nickname = nameField.getText().trim();
            if (!nickname.isEmpty()) {
                dispose(); // 시작 화면 닫기
                new MBTITestFrame(nickname).setVisible(true); // 테스트 화면 열기
            } else {
                JOptionPane.showMessageDialog(this, "닉네임을 입력해주세요!", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startButton);
        panel.add(inputPanel, BorderLayout.CENTER);
        add(panel);
        
    	playBackgroundMusic("배경음악.mp3");

    }

	private void playBackgroundMusic(String musicFile) {
        new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(musicFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                Player player = new Player(bis); // JLayer Player
                player.play(); // 음악 재생
            } catch (Exception e) {
                System.err.println("배경음악 재생 오류: " + e.getMessage());
            }
        }).start();


}

// MBTI 테스트 프레임
class MBTITestFrame extends JFrame {
   

	private final String nickname;
    private final CardLayout cardLayout = new CardLayout(); // 카드 레이아웃으로 질문 화면 전환
    private final JPanel mainPanel = new JPanel(cardLayout); // 메인 패널
    private final Map<String, Integer> scores = new HashMap<>(); // MBTI 점수 저장
    private int currentQuestion = 0; // 현재 질문 번호

    // 질문 리스트: 기본 질문과 미디어 질문 포함
    private final Question[] questions = {
    		
    		//E vs I
    		new BasicQuestion("1. 음식점에 가서 음식을 시켜려 하는데, 벨이 없고 웨이터도 나를 보지 않는다.", "언젠간 보겠지.. 손을 들고 기다린다.", "당장 소리친다. 여기요~!!!", "I", "E"),
    		//N vs S
            new BasicQuestion("2. 내가 원하는 배움은?", "훗날 나에게 도움이 될 것 같은 배움", "현재 나의 부족한 부분을 메꿀 수 있는 배움", "N", "S"),
    		//F vs T
            new BasicQuestion("3. '나 어제 접촉사고 났어!' 라는 친구의 전화를 받고 나는", "많이 놀랐겠다... 다친 데는 없어?", "정말? 보험처리는 했어?", "F", "T"),
    		//J vs P
            new BasicQuestion("4. 과제를 받았다. 나는", "뭐부터 할지 계획을 세워야 틀이 잡힌다.", "자료조사부터 해 봐야 틀이 잡힌다.", "J", "P"),
    		//E vs I
            new ImageQuestion("5. 둘 중 내가 놓이고 싶은 상황은?", new File("파티이미지.jpg"), new File("쉬는이미지.png"), "E", "I"),
    		//N vs S            
            new ImageQuestion("6. 둘 중 내 마음에 더 드는 그림은?", new File("상상화.png"), new File("정물화.jpg"), "N", "S"),
    		//F vs T
            new ImageQuestion("7. '나 아파...'라고 말했을 때 '병원에 가'라는 말을 어떻게 받아들이나요?", new File("F짤.jpg"), new File("T짤.jpg"), "F", "T"),
    		//J vs P
            new ImageQuestion("8. 영주 여행을 가기로 했다. 하루 전 나의 모습은?", new File("J짤.png"), new File("P짤.png"), "J", "P"),
            //E vs I
            new MediaQuestion("9. 두 음악을 각각 듣고 평소 즐겨듣는 스타일의 노래를 고르세요.", new File("E음악.mp3"), new File("I음악.mp3"), "E", "I"),
    		//N vs S
            new MediaQuestion("10. 두 음악을 각각 듣고 가사가 더 마음에 드는 음악을 고르세요.", new File("N음악.mp3"), new File("S음악.mp3"), "N", "S"),
            //F vs T
            new BasicQuestion("11. '근데 나는 너랑 취향이 진짜 안 맞는 것 같아.' 라는 말을 듣고 나는?", "(상처)원래 반대성향인 사람들이 잘 맞아.. ","그럴 수 있지! 사람은 다 다르니까.", "F", "T"),
    		//J vs P
            new TextQuestion("12. 오늘 하루, 당신의 계획을 작성해보세요.")
    };

    // 생성자
    public MBTITestFrame(String nickname) {
        this.nickname = nickname;
        setTitle("감각 MBTI TEST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        // 점수 초기화
        scores.put("E", 0);
        scores.put("I", 0);
        scores.put("N", 0);
        scores.put("S", 0);
        scores.put("F", 0);
        scores.put("T", 0);
        scores.put("J", 0);
        scores.put("P", 0);

     // 질문 패널 추가

        for (Question question : questions) {
            JPanel questionPanel = question.getPanel(() -> {
                if (currentQuestion < questions.length - 1) {
                    currentQuestion++;
                    cardLayout.next(mainPanel);
                } else {
                    displayResults();
                }
            }, scores);
            
            // 버튼 크기를 100x100으로 고정 설정
            for (Component component : questionPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setPreferredSize(new Dimension(100,100)); // 버튼 크기 고정
                    button.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                }
            }

            mainPanel.add(questionPanel, question.getText());
        }


        add(mainPanel);
        mainPanel.setBackground(new Color(70,130,180)); // 진한 하늘색 배경

        setFontAndColor(mainPanel);

    }
    // 글꼴 및 버튼 스타일 설정
    private void setFontAndColor(JComponent component) {
        Font font = new Font("SansSerif", Font.BOLD, 15); // 가독성 높은 글씨체 및 크기
        for (Component child : component.getComponents()) {
            if (child instanceof JButton) {
                JButton button = (JButton) child;
                button.setFont(font);
                button.setBackground(new Color(0,128,128)); //버튼배경색:청록색
                button.setForeground(Color.white); // 버튼글씨색: 하얀색
                button.setFocusPainted(false);
            } else if (child instanceof JLabel) {
                ((JLabel) child).setFont(font);
            } else if (child instanceof JPanel) {
                setFontAndColor((JComponent) child);
            }
        }
    }


    // 결과를 계산하고 표시
    private void displayResults() {
        StringBuilder resultText = new StringBuilder("테스트 결과:\n");

        // MBTI 유형 계산
        StringBuilder mbti = new StringBuilder();
        mbti.append(scores.get("E") > scores.get("I") ? "E" : "I");
        mbti.append(scores.get("N") > scores.get("S") ? "N" : "S");
        mbti.append(scores.get("F") > scores.get("T") ? "F" : "T");
        mbti.append(scores.get("J") > scores.get("P") ? "J" : "P");

        resultText.append(nickname).append("님의 MBTI는: ").append(mbti).append("\n");

        // 결과를 파일에 저장
        try (FileWriter writer = new FileWriter("test_results.txt")) {
            writer.write(resultText.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "결과를 파일에 저장하는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }

        // MBTI별 설명 파일 읽기
        String description = "설명 파일을 불러오는 중 오류 발생"; // 기본값
        try {
            description = Files.readString(Paths.get(mbti + ".txt"));
        } catch (IOException e) {
            description = "설명을 불러오는 데 실패했습니다.";
        }

     // 결과 화면 구성
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel1 = new JLabel("<html><h2 style='font-size: 25px;'>[ 테스트 결과 ]</h2></html>", SwingConstants.CENTER);
        resultPanel.add(resultLabel1, BorderLayout.NORTH);
        resultLabel1.setFont(new Font("SansSerif", Font.BOLD, 40));

        JLabel resultLabel2 = new JLabel("<html><h2 style='font-size: 20px;'>" + nickname + "님의 MBTI: " + mbti + "</h2 style='font-size: 15px;><p>" + description + "</p></html>");
        resultLabel2.setHorizontalAlignment(SwingConstants.CENTER); // 정렬 설정
        resultPanel.add(resultLabel2, BorderLayout.CENTER);

        // MBTI별 캐릭터 이미지 추가
        JLabel characterImageLabel = new JLabel(new ImageIcon(mbti + ".png")); // MBTI에 따른 캐릭터 이미지
        resultPanel.add(characterImageLabel, BorderLayout.SOUTH);

        // 마무리 화면 전환
        mainPanel.add(resultPanel, "결과");
        cardLayout.show(mainPanel, "결과");
    }
}
} 