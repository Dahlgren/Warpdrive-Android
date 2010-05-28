package at.dahlgren.warpdrive;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	public static List<Quote> parseQuotes (String html) {
		ArrayList<Quote> quotes = new ArrayList<Quote>();

		Pattern pattern = Pattern.compile(
				"(?:<div id=\"c_)([0-9]*)(?:\">)" + // ID
				"(?:.*?)" +
				"(?:>\\+</a>)(.*?)(?:<a)" + // Rank
				"(?:.*?)" +
				"(?:<tt>)(.*?)(?:</tt>)" // Text
				, Pattern.DOTALL);


		Matcher match = pattern.matcher(html);

		while(match.find()) {
			int id = Integer.parseInt(match.group(1));
			int rank = Integer.parseInt(match.group(2).trim());
			String text = match.group(3);
			
			quotes.add(new Quote(id, rank, text));
		}

		return quotes;
	}
	
	public static List<Comment> parseComments (String html) {
		ArrayList<Comment> comments = new ArrayList<Comment>();

		Pattern pattern = Pattern.compile(
				"(?:<a href='/profil/[0-9]*'>)(.*?)(?:</a>)" + // User
				"(?: : )" +
				"(.*?)(?:</div>)" + // Date
				"(?:.*?)" +
				"(?:<div class='quote_commenttext'>)(.*?)(?:</div>)" // Text
				, Pattern.DOTALL);


		Matcher match = pattern.matcher(html);

		while(match.find()) {
			String user = match.group(1);
			String date = match.group(2);
			String text = match.group(3);
			
			comments.add(new Comment(user, date, text));
		}

		return comments;
	}
}
