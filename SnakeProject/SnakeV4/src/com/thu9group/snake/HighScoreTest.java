package com.thu9group.snake;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;
/**
 * This is an isolated unit tests of Highscores.java and Gameover.java
 * NOTE:due to the fact that an android component inside these two mentioned classes, this
 * test contains clones of functionalities of the classes(without the android related code). It is difficult to test the whole 
 * class as is because there will be errors coming from buttons/editText/layout contents
 * @author wsim283
 *
 */
public class HighScoreTest extends TestCase {

	ArrayList<Integer> scores = new ArrayList<Integer>();
	public HighScoreTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		scores.add(10);
		scores.add(1);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	private int testCheckEligibility(int score) 
	{
	
		int indexToPush = -1;
		//newStrs is an arrayList of scores, if 0 that means no scores has been recorded
		if(scores.size() == 0){
			indexToPush  = 0;
		}
		else{
			for(int i = 0; i < scores.size(); i++){
				int currentScore = scores.get(i);
				if(score > currentScore){
					indexToPush = i;
					break;
				}
			}

			if(indexToPush == -1 && scores.size() < GameOver.TOP_SCORES){
				indexToPush = scores.size();
			}

		}
		return indexToPush;
	}

	private boolean  addScore(int score){
		int indexToBeInserted = testCheckEligibility(score);
		boolean success = false;

		//StringBuffer playerScore = new StringBuffer();
		//playerScore.append(getPlayersName() + “\tmostRecentScore”); //getPlayersName() prompts the user to enter his/hername
		if(indexToBeInserted != -1){
			if(scores.size() == 0){
				scores.add(score);
				System.err.println("successfully added score of: " + score);
			}
			else{ 
				System.err.println("successfully added score to INDEX: " + indexToBeInserted);
				scores.add(indexToBeInserted,score);
				if(scores.size()> GameOver.TOP_SCORES){
					scores.remove(GameOver.TOP_SCORES);
				}
			}
			success = true;
		}

		return success;
	}
	
	
	@Test
	public void testValidInsert(){
		assertEquals(true, addScore(3));
		assertEquals(true, addScore(12));
		assertEquals(true, addScore(1));
		/*
		 * check order of scores
		 */
		int cur = scores.get(0);
		assertEquals(12, cur);
		cur = scores.get(1);
		assertEquals(10, cur);
		cur = scores.get(2);
		assertEquals(3, cur);
		cur = scores.get(3);
		assertEquals(1, cur);
		cur = scores.get(4);
		assertEquals(1, cur);
	
	
		
	}
	@Test
	public void testInValidInsert(){
		System.err.println("size of arraylist: "+ scores.size());
		addScore(10);
		addScore(10);
		addScore(10);
		addScore(10);
		assertEquals(false, addScore(7));
		assertEquals(false, addScore(-1));
		assertEquals(false, addScore(-5));
		
	}
}
