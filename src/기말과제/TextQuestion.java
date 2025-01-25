package 기말과제;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// 텍스트 입력 질문을 처리하는 클래스 TextQuestion
// 사용자가 텍스트를 입력하고, 100자 이상인지 확인하여 점수를 업데이트하는 질문을 생성하는 클래스
public class TextQuestion extends Question {
    private JTextArea inputTextArea;  // 텍스트 입력을 위한 JTextArea
    private JButton submitButton;    // 제출 버튼

    // 생성자: 질문 텍스트를 받아서 부모 클래스 Question의 생성자를 호출
    public TextQuestion(String questionText) {
        super(questionText);  // 부모 클래스의 생성자 호출
    }

    // 텍스트 길이를 체크하고 점수를 업데이트하는 메서드
    // 50자 이상 입력하면 'J' 점수 +1, 50자 미만 입력하면 'P' 점수 +1
    private void checkTextLength(String inputText, Map<String, Integer> scores) {
        if (inputText.length() >= 50) {
            scores.put("J", scores.get("J") + 1);  // 50자 이상이면 'J' 점수 +1
        } else {
            scores.put("P", scores.get("P") + 1);  // 50자 미만이면 'P' 점수 +1
        }
    }

    @Override
    // 텍스트 입력 질문에 대한 패널을 생성하고 반환하는 메서드
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 255));  // 패널 배경 색 설정

        // 질문 텍스트를 JLabel로 표시
        JLabel questionLabel = new JLabel("<html><h2>" + getText() + "</h2></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // 글꼴 설정
        panel.add(questionLabel, BorderLayout.NORTH);  // 질문 레이블을 패널의 북쪽에 추가

        // 텍스트 입력을 위한 JTextArea 설정
        inputTextArea = new JTextArea(10, 30);  // 10줄 30열 크기
        inputTextArea.setWrapStyleWord(true);  // 단어가 끊어지지 않도록 설정
        inputTextArea.setLineWrap(true);  // 줄 바꿈을 허용
        JScrollPane scrollPane = new JScrollPane(inputTextArea);  // 스크롤 가능하도록 설정
        panel.add(scrollPane, BorderLayout.CENTER);  // 텍스트 입력 필드를 패널에 추가

        // 제출 버튼 생성
        submitButton = new JButton("제출");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));  // 글꼴 설정
        submitButton.setBackground(new Color(135, 206, 250));  // 버튼 배경 색
        submitButton.setForeground(Color.WHITE);  // 버튼 글자 색

        // 제출 버튼 클릭 시 텍스트 길이 확인 후 점수 업데이트
        submitButton.addActionListener(e -> {
            String inputText = inputTextArea.getText().trim();  // 입력된 텍스트 가져오기
            if (inputText.isEmpty()) {  // 텍스트가 비어있으면 경고창 띄움
                JOptionPane.showMessageDialog(
                    panel, 
                    "글자를 입력해주세요.",  // 경고 메시지
                    "경고", 
                    JOptionPane.WARNING_MESSAGE  // 경고 메시지 박스
                );
            } else {
                checkTextLength(inputText, scores);  // 텍스트 길이에 따라 점수 업데이트
                onNext.run();  // 다음 질문으로 넘어가게 함
            }
        });

        // 제출 버튼을 배치할 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);  // 버튼 패널을 투명하게 설정
        buttonPanel.add(submitButton);  // 제출 버튼을 버튼 패널에 추가
        panel.add(buttonPanel, BorderLayout.SOUTH);  // 버튼 패널을 패널의 하단에 추가

        return panel;  // 생성된 패널 반환
    }
}
