import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

class TrainingCoursesHandler extends TypedHandlerDelegate {

    private int count = 0;

    private final String text = "import {BetterSkills, createPersonFrom} from \"./person\";\n" +
            "import {createWorkshop, CodingCourse, UpcomingEditions} from \"./workshop\";\n" +
            "\n" +
            "\n" +
            "export const wantToLevelUp = async (): Promise<BetterSkills> => {\n" +
            "  const you = createPersonFrom('United States')\n" +
            "  const sustainableCodingWorkshop = createWorkshop(CodingCourse)\n" +
            "\n" +
            "  you.findTrainingCoursesAt('https://leanmind.es/en/training-courses/')\n" +
            "  if (you.areInEarlyDate()) {\n" +
            "    you.haveADiscountIn(sustainableCodingWorkshop)\n" +
            "  }\n" +
            "  you.enrollToday(sustainableCodingWorkshop.edition(UpcomingEditions))\n" +
            "  await you.attendWorkshop()\n" +
            "\n" +
            "  return you.withBetterSkills()\n" +
            "}";

    @NotNull
    @Override
    public Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        final Document document = editor.getDocument();
        Runnable runnable = () -> {
            boolean isTheEndOfTheText = count >= text.length();
            if (isTheEndOfTheText) {
                document.setText(text);
            } else {
                // 👇🏽 As agreed, the HackerTyper will type 2 character with every keystroke
                count += 4; // we have changed it to make the video when we record it faster
                document.setText(text.substring(0, count));
                updateCaretPosition(editor, document);
            }
        };
        WriteCommandAction.runWriteCommandAction(project, runnable);
        return Result.STOP;
    }


    private static void updateCaretPosition(@NotNull Editor editor, Document document) {
        // 👇🏽 In order to have the caret right after where we are typing,
        // the caret needs to be 1 line above the last one
        int line = document.getLineCount() - 1;
        int column = document.getLineEndOffset(line);
        LogicalPosition position = new LogicalPosition(line, column);
        CaretModel caretModel = editor.getCaretModel();
        caretModel.moveToLogicalPosition(position);
    }

}
