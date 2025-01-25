package 기말과제;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

// 이미지 질문을 처리하는 클래스 ImageQuestion
// 이미지 2개를 선택하는 질문을 생성하는 클래스
public class ImageQuestion extends AbstractMediaQuestion {

    // 생성자: 텍스트, 이미지 파일 2개, 선택 항목 2개를 초기화
    public ImageQuestion(String text, File image1, File image2, String type1, String type2) {
        super(text, image1, image2, type1, type2);  // 부모 클래스인 AbstractMediaQuestion의 생성자 호출
    }

    @Override
    // 패널을 생성하고, 이미지를 보여주는 GUI 컴포넌트를 반환하는 메서드
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        // 기본적인 질문 텍스트와 함께 패널 생성
        JPanel panel = createBasePanel(getText());

        // 이미지를 표시할 패널을 생성하고, 1행 2열의 GridLayout으로 배치
        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        imagePanel.setOpaque(false);  // 배경을 투명하게 설정

        // 이미지를 크기 조정하여 JLabel에 표시
        JLabel imageLabel1 = createScaledImageLabel(file1);
        JLabel imageLabel2 = createScaledImageLabel(file2);

        // 첫 번째 이미지 클릭 시 점수 갱신
        imageLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scores.put(type1, scores.get(type1) + 1);  // 해당 타입의 점수 +1
                onNext.run();  // 다음 질문으로 넘어감
            }
        });

        // 두 번째 이미지 클릭 시 점수 갱신
        imageLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scores.put(type2, scores.get(type2) + 1);  // 해당 타입의 점수 +1
                onNext.run();  // 다음 질문으로 넘어감
            }
        });

        // 이미지를 패널에 추가
        imagePanel.add(imageLabel1);
        imagePanel.add(imageLabel2);

        // 이미지 패널을 전체 패널에 추가
        panel.add(imagePanel, BorderLayout.CENTER);

        return panel;  // 패널 반환
    }

    // 이미지를 크기 조정하여 JLabel로 반환하는 메서드
    private JLabel createScaledImageLabel(File imageFile) {
        ImageIcon originalIcon = new ImageIcon(imageFile.getPath());  // 파일에서 이미지 로드
        Image originalImage = originalIcon.getImage();  // 이미지 객체 생성
        Image scaledImage = originalImage.getScaledInstance(300, 350, Image.SCALE_SMOOTH);  // 크기 조정
        return new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);  // 크기 조정된 이미지를 JLabel로 반환
    }
}
