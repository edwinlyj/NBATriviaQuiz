package com.example.a16022895.nbatriviaquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a16022895.nbatriviaquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NBATriviaQuiz";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("What franchise has played in the most NBA finals since 1947?", "Lakers", "Celtics", "Bulls", 1);
        addQuestion(q1);

        Question q2 = new Question("Which was the year linsanity took the world by storm?", "2009", "2011", "2012", 3);
        addQuestion(q2);

        Question q3 = new Question("What player has the highest career FT percentage?", "Dirk Nowitzki", "Steve Nash", "Michael Jordan", 2);
        addQuestion(q3);

        Question q4 = new Question("Who was the #1 draft pick in 2004?", "Lebron James", "Dwight Howard", "Derrick Rose", 2);
        addQuestion(q4);

        Question q5 = new Question("Which player is not in the HOF", "Yao Ming", "Scottie Pippen", "Ben Wallace", 3);
        addQuestion(q5);

        Question q6 = new Question("Which player is the NBA logo designed after", "Jerry West", "Kareem Abdul-Jabbar", "Michael Jordan", 1);
        addQuestion(q6);

        Question q7 = new Question("What team has the best record in one season?", "Chicago Bulls", "Golden State Warriors", "Los angles Lakers", 2);
        addQuestion(q7);

        Question q8 = new Question("Which of these players is a NBA Champion", "Damian Lillard", "Russel Westbrook", "Dirk Nowitzki", 3);
        addQuestion(q8);

        Question q9 = new Question("What player has the highest career PPG?", "Michael Jordan", "Larry Bird", "Kareem Abdul-Jabbar", 1);
        addQuestion(q9);

        Question q10 = new Question("What seven-foot-one NBA center's first name translates as \"little one\"?", "Manute Bol", "Shaquille O'Neal", "Kristaps Porziņģis", 2);
        addQuestion(q10);

    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswer());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
