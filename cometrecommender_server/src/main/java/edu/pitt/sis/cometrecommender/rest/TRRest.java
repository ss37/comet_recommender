package edu.pitt.sis.cometrecommender.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.pitt.sis.cometrecommender.models.TalkModel;
import edu.pitt.sis.cometrecommender.models.InterestingTalksModel;
import edu.pitt.sis.documentmodel.TestDocumentModel;

/**
 * This class is used to receive all the requests for calculating the recommended list of upcoming talks.
 * @author SHRUTI
 *
 */
@Path("/talks")
public class TRRest {

	/**
	 * Receives requests for calculating user-based recommendations
	 * @param talkModel
	 * @return Response of recommended list of upcoming talks
	 */
	@POST
	@Path("/interesting")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInterestingTalks(final TalkModel talkModel){
		try{
			TestDocumentModel t = new TestDocumentModel();
			List<InterestingTalksModel> list = t.getInterestingTalks(talkModel.getBookmarks(), talkModel.getUpcoming());
			GenericEntity<List<InterestingTalksModel>> entity = new GenericEntity<List<InterestingTalksModel>>(list) {};
			return Response.status(200).entity(entity).build();
		}
		catch(Exception ex) {
			return Response.status(500).build();
		}
	}
}
