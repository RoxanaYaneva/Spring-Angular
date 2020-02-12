import React from "react";
import Comment from './Comment';

class CommentSection extends React.Component {
  render() {
    return (
      <div>
        {this.props.comments.map(comment =>
          <Comment key={comment.id} comment={comment} />
        )}
      </div>
    );
  }
}


export default CommentSection;
