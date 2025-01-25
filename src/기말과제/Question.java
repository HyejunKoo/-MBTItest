package 기말과제;
import javax.swing.*;
import java.util.Map;

// 질문을 표현하는 추상 클래스 Question
// 이 클래스는 모든 질문의 기본 형태를 정의하며, 각 질문에 맞는 패널을 생성하는 추상 메서드를 포함
abstract class Question {
    private final String text;  // 질문 텍스트를 저장하는 변수

    // 생성자: 질문 텍스트를 초기화
    public Question(String text) {
        this.text = text;
    }

    // 질문 텍스트를 반환하는 메서드
    public String getText() {
        return text;
    }

    // 각 질문에 맞는 패널을 생성하는 추상 메서드
    // onNext는 다음 질문으로 넘어갈 때 실행할 메서드, scores는 점수 맵
    public abstract JPanel getPanel(Runnable onNext, Map<String, Integer> scores);
}
