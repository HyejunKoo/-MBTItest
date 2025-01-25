package 기말과제; 

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javazoom.jl.player.Player;

// 미디어 기반 질문을 처리하는 MediaQuestion 클래스 (AbstractMediaQuestion을 상속)
public class MediaQuestion extends AbstractMediaQuestion {
    private Player[] players = new Player[2];  // 두 개의 음악을 제어할 Player 객체 배열

    // 생성자: 질문 텍스트와 두 개의 미디어 파일, 점수 유형을 초기화
    public MediaQuestion(String text, File media1, File media2, String type1, String type2) {
        super(text, media1, media2, type1, type2);  // 부모 클래스 AbstractMediaQuestion의 생성자 호출
    }

    // 질문에 해당하는 패널을 반환하는 메서드
    @Override
    public JPanel getPanel(Runnable onNext, Map<String, Integer> scores) {
        // 기본 패널 생성 (질문 텍스트 포함)
        JPanel panel = createBasePanel(getText());  // 질문 텍스트를 포함하는 기본 패널 생성

        // 미디어 버튼들을 담을 패널 설정 (음악 재생 및 정지 버튼)
        JPanel mediaPanel = new JPanel(new GridLayout(1, 4, 10, 10));  // 그리드 레이아웃으로 1행 4열 배치
        Dimension buttonSize = new Dimension(100, 80);  // 버튼 크기 설정

        // 음악 1과 2의 재생 및 정지 버튼 생성
        JButton playButton1 = createButton("음악 1 재생", buttonSize, Color.CYAN, () -> playMedia(0, file1));
        JButton stopButton1 = createButton("음악 1 정지", buttonSize, Color.RED, () -> stopMedia(0));
        JButton playButton2 = createButton("음악 2 재생", buttonSize, Color.CYAN, () -> playMedia(1, file2));
        JButton stopButton2 = createButton("음악 2 정지", buttonSize, Color.RED, () -> stopMedia(1));

        // 재생/정지 버튼들을 mediaPanel에 추가
        mediaPanel.add(playButton1);
        mediaPanel.add(stopButton1);
        mediaPanel.add(playButton2);
        mediaPanel.add(stopButton2);

        // 음악을 선택하는 버튼들을 담을 패널 설정
        JPanel choicePanel = new JPanel(new GridLayout(1, 2, 10, 10));  // 1행 2열로 선택 버튼 배치
        JButton chooseButton1 = createButton("음악 1 선택", buttonSize, Color.GREEN, () -> {
            scores.put(type1, scores.get(type1) + 1);  // 음악 1을 선택하면 점수 업데이트
            endMediaQuestion(onNext);  // 질문 종료 후 후속 작업 실행
        });

        JButton chooseButton2 = createButton("음악 2 선택", buttonSize, Color.GREEN, () -> {
            scores.put(type2, scores.get(type2) + 1);  // 음악 2를 선택하면 점수 업데이트
            endMediaQuestion(onNext);  // 질문 종료 후 후속 작업 실행
        });

        // 선택 버튼들을 choicePanel에 추가
        choicePanel.add(chooseButton1);
        choicePanel.add(chooseButton2);

        // 패널에 미디어 버튼과 선택 버튼 패널을 추가
        panel.add(mediaPanel, BorderLayout.CENTER);
        panel.add(choicePanel, BorderLayout.SOUTH);

        return panel;  // 최종 패널 반환
    }

    // 음악 재생 메서드: 주어진 인덱스와 파일로 음악을 재생
    private void playMedia(int index, File mediaFile) {
        stopMedia(index);  // 현재 재생 중인 음악을 멈추고 새로운 음악 재생
        try {
            // FileInputStream을 통해 파일을 읽고, Player 객체로 음악을 재생
            FileInputStream fis = new FileInputStream(mediaFile);
            players[index] = new Player(fis);  // 해당 인덱스에 Player 객체 생성
            new Thread(() -> {
                try {
                    players[index].play();  // 새로 생성된 Player로 음악 재생
                } catch (Exception ex) {
                    ex.printStackTrace();  // 예외 처리
                }
            }).start();  // 별도의 스레드에서 음악을 재생
        } catch (Exception ex) {
            ex.printStackTrace();  // 예외 처리
        }
    }

    // 음악 정지 메서드: 주어진 인덱스에 해당하는 음악을 정지
    private void stopMedia(int index) {
        if (players[index] != null) {
            players[index].close();  // 음악 정지
            players[index] = null;  // Player 객체를 null로 설정
        }
    }

    // 질문 종료 후 후속 처리를 위한 메서드
    private void endMediaQuestion(Runnable onNext) {
        // 모든 음악을 정지시키고, 후속 작업 실행
        for (int i = 0; i < players.length; i++) {
            stopMedia(i);
        }
        onNext.run();  // 후속 작업 실행 (다음 질문으로 넘어가기)
    }
}