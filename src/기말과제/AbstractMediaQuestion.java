package 기말과제;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// 미디어 관련 질문을 처리하는 추상 클래스
// 이미지, 음악 등 미디어 파일을 처리할 때 공통적인 요소들을 포함하고 있음
public abstract class AbstractMediaQuestion extends Question {
    // 미디어 파일들을 저장할 필드
    protected final File file1;
    protected final File file2;
    protected final String type1;
    protected final String type2;

    // 생성자: 질문 텍스트와 두 개의 미디어 파일, 타입을 받음
    public AbstractMediaQuestion(String text, File file1, File file2, String type1, String type2) {
        super(text);  // 부모 클래스 Question의 생성자 호출
        this.file1 = file1;
        this.file2 = file2;
        this.type1 = type1;
        this.type2 = type2;

        // 파일 유효성 검사
        validateFiles(file1, file2);
    }

    // 파일 유효성 검사 메서드: 파일이 존재하고 유효한지 확인
    private void validateFiles(File... files) {
        for (File file : files) {
            if (!file.exists() || !file.isFile()) {  // 파일이 존재하지 않거나 파일이 아니면 예외 처리
                throw new IllegalArgumentException("유효하지 않은 파일: " + file.getPath());
            }
        }
    }

    // 공통 버튼 생성 메서드
    // 버튼을 생성하고 설정하는 메서드로, 크기, 배경 색, 동작을 매개변수로 받아 버튼을 반환
    protected JButton createButton(String text, Dimension size, Color bgColor, Runnable action) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);  // 버튼 크기 설정
        button.setBackground(bgColor);  // 버튼 배경 색 설정
        button.addActionListener(e -> action.run());  // 버튼 클릭 시 실행할 액션 설정
        return button;  // 생성된 버튼 반환
    }

    // 공통 레이아웃 설정 메서드
    // 각 미디어 질문에 대한 기본 패널 레이아웃을 설정
    protected JPanel createBasePanel(String questionText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 255));  // 패널 배경 색 설정

        // 질문 텍스트를 JLabel로 표시
        JLabel questionLabel = new JLabel("<html><h2>" + questionText + "</h2></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  // 글꼴 설정
        panel.add(questionLabel, BorderLayout.NORTH);  // 패널의 상단에 질문 레이블 추가

        return panel;  // 생성된 패널 반환
    }
}
