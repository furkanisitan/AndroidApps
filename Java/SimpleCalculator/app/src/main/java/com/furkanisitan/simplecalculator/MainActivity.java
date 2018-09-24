package com.furkanisitan.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    HorizontalScrollView hsv1, hsv2;
    TextView textView1, textView2;
    String text1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        hsv1 = findViewById(R.id.horizontalScrollView1);
        hsv2 = findViewById(R.id.horizontalScrollView2);

        textView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override   // Text her değiştiğinde ScrollView en sağa çekilir.
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ViewTreeObserver vto = hsv1.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        hsv1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        hsv1.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                hsv1.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
        textView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ViewTreeObserver vto = hsv2.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        hsv2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        hsv2.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                hsv2.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    public void clickOperandandBrackets(View view) {   // Operand ve parantez ekleme
        Button button = (Button) view;
        textView1.setText(text1 += button.getText());
        printResult();  // TextView2 ye TextView1 deki işlemin sonucunu yazdır.
    }

    public void clickOperator(View view) {          // Operator ekleme ( '-' hariç)
        Button button = (Button) view;
        if (!text1.isEmpty()) {
            int textLength = text1.length();
            char lastChar = text1.charAt(textLength - 1);
            if (lastChar != '(') {
                int index = "+-÷x".indexOf(lastChar);
                if (index < 0)    // Son eleman operator değilse
                    textView1.setText(text1 += button.getText());
                else {
                    char secondLastChar = textLength > 1 ? text1.charAt(text1.length() - 2) : '\0';
                    if (secondLastChar != '(')
                        textView1.setText(text1 = text1.replaceFirst(".$", "") + button.getText());
                    // replaceFirst(".$","") => Son karakteri sil
                }
            }
        }
        printResult();
    }

    public void clickSubtract(View view) {

        if (text1.isEmpty())
            textView1.setText(text1 = "-");
        else {
            char lastChar = text1.charAt(text1.length() - 1);
            if (lastChar == '+')
                textView1.setText(text1 = text1.replaceFirst(".$", "") + "-");
            else if (lastChar != '-')
                textView1.setText(text1 += "-");
        }
        printResult();
    }

    public void clickVirgule(View view) {

        if (text1.isEmpty())
            textView1.setText(text1 = ",");
        else {
            char operators[] = "-÷x+".toCharArray();
            int maxIndex = 0;
            for (char ch : operators)
                maxIndex = Math.max(maxIndex, text1.lastIndexOf(ch));
            textView1.setText(text1 += text1.substring(maxIndex).indexOf(',') < 0 ? "," : "");
        }
        printResult();
    }

    public void clickClear(View view) {
        textView1.setText(text1 = "");
        textView2.setText("");
    }

    public void clickDel(View view) {
        int text1length = text1.length();
        text1 = text1.replaceAll("[0-9]+,?[0-9]*[E][+-÷x][0-9]+[^+-÷x]*$", ""); // Bu kısım en alt satırlarda anlatıldı
        if (text1length == text1.length())
            text1 = text1.replaceFirst(".$", "");
        textView1.setText(text1);
        printResult();
    }

    public void clickEqual(View view) {
        String text2 = textView2.getText().toString();
        if (!(text2.equals(getResources().getString(R.string.divisionbyzero)) || text2.equals(getResources().getString(R.string.error))))
            textView1.setText(text1 = text2);
    }

    public void printResult() {

        if (!text1.isEmpty()) {
            String postfix, result;
            if ((postfix = Calculator.infixtoPostfix(text1.replace(',', '.'))) == null)
                textView2.setText(R.string.error);
            else if ((result = Calculator.postfixCalculator(postfix)) == null)
                textView2.setText(R.string.divisionbyzero);
            else {
                if (result.indexOf('.') >= 0)
                    result = result.replaceFirst("0*$", "").replaceFirst("[.]$", "");
                // Sondaki tüm sıfırları sil. Sıfırlar silindikten sonra son karakter nokta('.') ise onu da sil.
                textView2.setText(result.replace('.', ','));
            }
        } else
            textView2.setText("");
    }
}

/*
Eğer text1 de ki son ifade 'E' ile oluşturulmuş bir sayı ise bu ifade bütün olarak silinir.
Örn: "24+35-2.14789E+1236" gibi bir ifadenin delete işlemi dönüşü "24+35-" olmalıdır.

replaceAll("[0-9]+,?[0-9]*[E][+-÷x][0-9]+[^+-÷x]*$", ""); kodunun çalışma mantığı;
    - Bu metod ilk argüman olarak verilen düzenli ifadeyi(regular expression) bulursa "" ifadesiyle değiştirecek.Yani silecek.
    Sırasıyla;
    [0-9]+  => 1 veya daha fazla rakam,
    ,?      => 0 veya 1 tane virgül,
    [0-9]*  => Herhangi sayıda rakam,
    [E]     => 1 tane 'E',
    [+-÷x]  => 1 tane "+-÷x" karakterlerinden herhangi biri,
    [0-9]+  => 1 veya daha fazla rakam,
    [^+-÷x]*$ => Herhangi sayıda, sona kadar giden karakterlerin hepsi "+-÷x" karakterlerinden farklı olmalı.
*/

