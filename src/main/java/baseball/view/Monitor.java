package baseball.view;

import baseball.constant.OptionSetting;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.Arrays;

public class Monitor {
    private int[] answer;

    public void gameStart() {
        createAnswer();

        for (int val : answer) {
            System.out.println(val);
        }
    }

    public void createAnswer() {
        answer = new int[3];

        for (int i = 0; i < 3; i++) {
            answer[i] = Randoms.pickNumberInRange(
                    OptionSetting.MIN_NUM,
                    OptionSetting.MAX_NUM
            );
        }

        if (!validateResult(answer)) {
            createAnswer();

            return;
        }

        Arrays.sort(answer);
    }

    private boolean validateResult(int[] answer) {
        return answer[0] != answer[1]
                && answer[0] != answer[2]
                && answer[1] != answer[2];
    }

    private int getStrikeCount(char[] ch) {
        int strike_cnt = 0;

        for(int i = 0; i < 3; i++) {
            if(ch[i] - '0' == answer[i]) {
                strike_cnt++;
            }
        }

        return strike_cnt;
    }

    private int getBallCount(char[] ch) {
        int ball_cnt = 0;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i != j && ch[i] == answer[j]) {
                    ball_cnt++;
                }
            }
        }

        return ball_cnt;
    }
}
