package com.oh5.baash.tinymonsters.patterns;

/**
 * Created by davidrawk on 7/01/15.
 */
public class GreaterLessPattern extends BasePattern {
    private int m_variable1, m_variable2;
    private String m_right_answer;
    public GreaterLessPattern(int max_variable){
        description = "Compare the numbers";
        m_variable1 = m_randomiser.nextInt(max_variable) + 1;
        m_variable2 = m_variable1;
        switch(m_randomiser.nextInt(3)){
            case 0: m_right_answer = "<";
                    m_variable2 += ( 1 + m_randomiser.nextInt(9));
            break;
            case 1: m_right_answer = "=";
            break;
            case 2: m_right_answer = ">";
                    m_variable2 -= ( 1 + m_randomiser.nextInt(9));
            break;
        }
        if(m_variable2 < 0) {
            m_variable2 -= m_variable2;
            m_variable1 -= m_variable2;
        }
    }

    public String get_right_answer(){ return m_right_answer; }
    public String [] get_answers(){
        if( m_answers == null) {
            m_answers = new String[3];
            m_answers[0] = "<";
            m_answers[1] = "=";
            m_answers[2] = ">";
        }
        return m_answers;
    }

    public String[] get_puzzle(){
        if( m_pattern == null){
            m_pattern = new String[3];
            m_pattern[0] = "" + m_variable1;
            m_pattern[1] = get_right_answer();
            m_pattern[2] = "" + m_variable2;
        }
        return m_pattern;
    }

    public int get_size(){
        return 3;
    }
}
