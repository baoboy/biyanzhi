package com.biyanzhi.task;

import com.biyanzhi.data.Comment;
import com.biyanzhi.enums.RetError;

public class SendCommentTask extends BaseAsyncTask<Comment, Void, RetError> {
	private Comment comment;
	private int picture_publisher_id;

	public SendCommentTask(int picture_publisher_id) {
		this.picture_publisher_id = picture_publisher_id;
	}

	@Override
	protected RetError doInBackground(Comment... params) {
		comment = params[0];
		RetError ret = comment.sendComment(picture_publisher_id);
		return ret;
	}

}
