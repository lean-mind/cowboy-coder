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

    private final String text = "import { SustainableCodingCourse } from \"./sustainable-coding-course\"\n" +
            "import { April, March, May } from \"./months\"\n" +
            "\n" +
            "const availableTrainingCourses = () => {\n" +
            "  const course = new SustainableCodingCourse()\n" +
            "  const nextCourses = [\n" +
            "    {\n" +
            "      place: 'Barcelona',\n" +
            "      dates: [new Date(2023, March, 30), new Date(2023, March, 31)],\n" +
            "      course,\n" +
            "    },\n" +
            "    {\n" +
            "      place: 'Madrid',\n" +
            "      dates: [new Date(2023, April, 19), new Date(2023, April, 20)],\n" +
            "      course,\n" +
            "    },\n" +
            "    {\n" +
            "      place: 'San Francisco Bay Area',\n" +
            "      dates: [new Date(2023, May, 18), new Date(2023, May, 19)],\n" +
            "      course,\n" +
            "    },\n" +
            "    {\n" +
            "      place: 'Chicago',\n" +
            "      dates: [new Date(2023, May, 25), new Date(2023, May, 26)],\n" +
            "      course,\n" +
            "    }\n" +
            "  ]\n" +
            "\n" +
            "  return nextCourses\n" +
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
