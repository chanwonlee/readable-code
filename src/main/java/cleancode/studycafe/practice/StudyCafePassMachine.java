package cleancode.studycafe.practice;

import cleancode.studycafe.practice.exception.AppException;
import cleancode.studycafe.practice.io.InputHandler;
import cleancode.studycafe.practice.io.OutputHandler;
import cleancode.studycafe.practice.io.StudyCafeFileHandler;
import cleancode.studycafe.practice.model.StudyCafeLockerPass;
import cleancode.studycafe.practice.model.StudyCafePass;
import cleancode.studycafe.practice.model.StudyCafePassType;
import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        printWelcomeMessage();
        try {
            StudyCafePassType studyCafePassType = getStudyCafePassTypeFromUserAction();
            StudyCafePass selectedPass = getPassesFromUserAction(studyCafePassType);
            if (studyCafePassType == StudyCafePassType.FIXED) {
                StudyCafeLockerPass lockerPass = getLockerPass(selectedPass);

                outputHandler.askLockerPass(lockerPass);
                boolean lockerSelection = inputHandler.getLockerSelection();

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    return;
                }
            }
            outputHandler.showPassOrderSummary(selectedPass, null);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void printWelcomeMessage() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
    }

    private StudyCafePassType getStudyCafePassTypeFromUserAction() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafePass getPassesFromUserAction(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = getStudyCafePasses(studyCafePassType);
        outputHandler.showPassListForSelection(studyCafePasses);
        return inputHandler.getSelectPass(studyCafePasses);
    }

    private StudyCafeLockerPass getLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                        && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

    private List<StudyCafePass> getStudyCafePasses(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }
}
