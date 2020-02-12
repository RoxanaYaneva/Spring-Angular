import React from 'react';
import { Grid, Typography, Divider } from '@material-ui/core';


class Comment extends React.Component {
  render() {
    // const { classes } = this.props;
    return (
      <Grid container justify="center">
        <Grid item xs={12}>
          <Typography variant="h6">Roxana Yaneva | {this.props.comment.created}</Typography>
        </Grid>
        <Divider />
        <Grid item xs={12}>
          <Typography>{this.props.comment.text}</Typography>
        </Grid>
        <br />
      </Grid>
    );
  }
}

export default Comment;