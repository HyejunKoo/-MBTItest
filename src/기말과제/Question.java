package �⸻����;
import javax.swing.*;
import java.util.Map;

// ������ ǥ���ϴ� �߻� Ŭ���� Question
// �� Ŭ������ ��� ������ �⺻ ���¸� �����ϸ�, �� ������ �´� �г��� �����ϴ� �߻� �޼��带 ����
abstract class Question {
    private final String text;  // ���� �ؽ�Ʈ�� �����ϴ� ����

    // ������: ���� �ؽ�Ʈ�� �ʱ�ȭ
    public Question(String text) {
        this.text = text;
    }

    // ���� �ؽ�Ʈ�� ��ȯ�ϴ� �޼���
    public String getText() {
        return text;
    }

    // �� ������ �´� �г��� �����ϴ� �߻� �޼���
    // onNext�� ���� �������� �Ѿ �� ������ �޼���, scores�� ���� ��
    public abstract JPanel getPanel(Runnable onNext, Map<String, Integer> scores);
}
