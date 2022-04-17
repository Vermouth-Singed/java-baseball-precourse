package baseball.view;

import baseball.constant.KorErrorMsg;
import baseball.constant.KorGamePlayMsg;
import baseball.constant.OptionSetting;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Monitor {
    private int[] answer;

    public void gameStart() {
        createAnswer();

        printMenu();
    }

    private void printMenu() {
        System.out.print(KorGamePlayMsg.PRESS_NUMBER_MSG);

        String nums = Console.readLine();

        try {
            validateCorrectNumberInput(nums);

            if (validateStrikeAndBall(nums)) {
                retryMsg();

                return;
            }
        } catch(IllegalArgumentException e) {
            System.out.println(KorErrorMsg.NUMBER_ERROR_MSG);

            throw new IllegalArgumentException();
        } catch(Exception e) {
            System.out.println(KorErrorMsg.ERROR_MSG);

            return;
        }

        printMenu();
    }

    private void retryMsg() {
        System.out.print(KorGamePlayMsg.GAME_OVER_MSG);

        String choice = Console.readLine();

        if ("1".equals(choice)) {
            gameStart();

            return;
        }

        if ("2".equals(choice)) {
            System.out.println(KorGamePlayMsg.GOOD_BYE_MSG);

            return;
        }

        throw new IllegalArgumentException();
    }

    private boolean validateStrikeAndBall(String nums) {
        int strike = getStrikeCount(nums);
        int ball = getBallCount(nums);

        printResult(ball, strike);

        return strike == 3;
    }

    private void printResult(int ball, int strike) {
        String result = "";

        if (ball > 0) {
            result += ball + KorGamePlayMsg.BALL_MSG;
        }

        if (ball > 0 && strike > 0) {
            result += " ";
        }

        if (strike > 0) {
            result += strike + KorGamePlayMsg.STRIKE_MSG;
        }

        if (strike + ball == 0) {
            result = KorGamePlayMsg.NOTHING_MSG;
        }

        System.out.println(result);
    }

    private void validateCorrectNumberInput(String nums) {
        if (nums == null || nums.length() != 3) {
            throw new IllegalArgumentException();
        }
        
        if (!validateBetweenOneAndNine(nums.charAt(0))
                || !validateBetweenOneAndNine(nums.charAt(1))
                || !validateBetweenOneAndNine(nums.charAt(2))) {
            throw new IllegalArgumentException();
        }

        if (nums.charAt(0) == nums.charAt(1)
            || nums.charAt(0) == nums.charAt(2)
            || nums.charAt(1) == nums.charAt(2)) {
            throw new IllegalArgumentException();
        }
    }
    
    private boolean validateBetweenOneAndNine(char ch) {
        return ch >= '1' && ch <= '9';
    }

    private void createAnswer() {
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
    }

    private boolean validateResult(int[] answer) {
        return answer[0] != answer[1]
                && answer[0] != answer[2]
                && answer[1] != answer[2];
    }

    private int getStrikeCount(String nums) {
        int strike_cnt = 0;

        for(int i = 0; i < 3; i++) {
            if (nums.charAt(i)-'0' == answer[i]) {
                strike_cnt++;
            }
        }

        return strike_cnt;
    }

    private int getBallCount(String nums) {
        int ball_cnt = 0;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i != j && nums.charAt(i)-'0' == answer[j]) {
                    ball_cnt++;
                }
            }
        }

        return ball_cnt;
    }
}
