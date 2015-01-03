package com.oh5.baash.littlebitofeverything.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class NumberPattern {
        public int m_right_answer;
        private int m_size = 10;
        private int [] m_answers = null;
        private int [] m_pattern = null;

        public NumberPattern(int size){
            m_right_answer = new Random().nextInt(20);
            m_size = size;
        }

        public int [] get_answers(){
            if( m_answers == null) {
                Random rand = new Random();
                m_answers = new int[3];
                int random_number;
                for(int x = 0; x < 3; x++){
                    random_number = rand.nextInt(7)+1;
                    if( rand.nextBoolean() ) {
                        random_number *= -1;
                    }
                    m_answers[x] = (m_right_answer + random_number + 20)%20;
                }
                int right_index = rand.nextInt(3);
                m_answers[right_index] = m_right_answer;
            }
            return m_answers;
        }

        public int[] get_pattern(){
            if( m_pattern == null) {
                m_pattern = new int[m_size];
                int lowest = m_right_answer - (new Random().nextInt(m_size / 2));
                if(lowest < 0)               lowest = 0;
                if((lowest + m_size) > 20 ) lowest = 20-m_size;

                for (int x = 0; x < m_size; x++) {
                    m_pattern[x] = lowest + x;
                }
                if(new Random().nextBoolean()) {
                    revers_pattern();
                }
            }
            return m_pattern;
        }

        private void revers_pattern(){
            for (int i = 0; i < m_size / 2; i++) {
                int temp = m_pattern[i];
                m_pattern[i] = m_pattern[m_size - 1 - i];
                m_pattern[m_size - 1 - i] = temp;
            }
        }
        public int size(){
            return m_size;
        }
}
