import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

class HackerTypeHandler extends TypedHandlerDelegate {

    private int count = 0;

    private final String text = "package com.gildedrose;\n" +
    "class GildedRose {\n" +
    "    Item[] items;\n" +
    "\n" +
    "    public GildedRose(Item[] items) {\n" +
    "        this.items = items;\n" +
    "    }\n" +
    "\n" +
    "    public void updateQuality() {\n" +
    "        for (int i = 0; i < items.length; i++) {\n" +
    "            if (!items[i].name.equals('Aged Brie')\n" +
    "                    && !items[i].name.equals('Backstage passes to a TAFKAL80ETC concert')) {\n" +
    "                if (items[i].quality > 0) {\n" +
    "                    if (!items[i].name.equals('Sulfuras, Hand of Ragnaros')) {\n" +
    "                        items[i].quality = items[i].quality - 1;\n" +
    "                    }\n" +
    "                }\n" +
    "            } else {\n" +
    "                if (items[i].quality < 50) {\n" +
    "                    items[i].quality = items[i].quality + 1;\n" +
    "\n" +
    "                    if (items[i].name.equals('Backstage passes to a TAFKAL80ETC concert')) {\n" +
    "                        if (items[i].sellIn < 11) {\n" +
    "                            if (items[i].quality < 50) {\n" +
    "                                items[i].quality = items[i].quality + 1;\n" +
    "                            }\n" +
    "                        }\n" +
    "\n" +
    "                        if (items[i].sellIn < 6) {\n" +
    "                            if (items[i].quality < 50) {\n" +
    "                                items[i].quality = items[i].quality + 1;\n" +
    "                            }\n" +
    "                        }\n" +
    "                    }\n" +
    "                }\n" +
    "            }\n" +
    "\n" +
    "            if (!items[i].name.equals('Sulfuras, Hand of Ragnaros')) {\n" +
    "                items[i].sellIn = items[i].sellIn - 1;\n" +
    "            }\n" +
    "\n" +
    "            if (items[i].sellIn < 0) {\n" +
    "                if (!items[i].name.equals('Aged Brie')) {\n" +
    "                    if (!items[i].name.equals('Backstage passes to a TAFKAL80ETC concert')) {\n" +
    "                        if (items[i].quality > 0) {\n" +
    "                            if (!items[i].name.equals('Sulfuras, Hand of Ragnaros')) {\n" +
    "                                items[i].quality = items[i].quality - 1;\n" +
    "                            }\n" +
    "                        }\n" +
    "                    } else {\n" +
    "                        items[i].quality = items[i].quality - items[i].quality;\n" +
    "                    }\n" +
    "                } else {\n" +
    "                    if (items[i].quality < 50) {\n" +
    "                        items[i].quality = items[i].quality + 1;\n" +
    "                    }\n" +
    "                }\n" +
    "            }\n" +
    "        }\n" +
    "    }\n" +
    "}\n";

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
                count += 2;
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
