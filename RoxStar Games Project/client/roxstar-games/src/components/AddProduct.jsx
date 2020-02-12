import React from 'react';
import { withStyles } from "@material-ui/core/styles";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import styles from './FormStyle.jsx';
import Container from '@material-ui/core/Container';
import sendRequest from "../Request.js";
import { notify } from 'react-notify-toast';

class AddProduct extends React.Component {
  constructor(props) {
    super(props);
    this.state = { title: '', genre: '', platform: '', type: '', studio: '' };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    const uri = '/api/products';
    var game = { title : this.state.title, platform : this.state.platform, genre : this.state.genre, type : this.state.type}; // add to studio field currently logged in user
    sendRequest(uri, 'POST',
      JSON.stringify(game),
      (response) => {
        // notify.show('You registered successfully', 'success', 1500);
        if (response) {
          this.props.history.push('/');
        }
      });
  }

  render() {
    const { classes } = this.props;
    return (
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography variant="h5">
            Add product
          </Typography>
          <form style={{marginBottom: 40}} className={classes.form} noValidate onSubmit={this.handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
              <input type='file' id='single' className={classes.upload} onChange={this.onChange} /> 
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  name="Title"
                  variant="outlined"
                  required
                  fullWidth
                  id="title"
                  label="Title"
                  autoFocus
                  value={this.state.title}
                  onChange={this.handleInputChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  id="platform"
                  label="Platform"
                  name="platform"
                  value={this.state.platform}
                  onChange={this.handleInputChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  id="genre"
                  label="Genre"
                  name="genre"
                  value={this.state.email}
                  onChange={this.handleInputChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  name="type"
                  label="Type"
                  id="type"
                  value={this.state.type}
                  onChange={this.handleInputChange}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              className={classes.submit}
              onClick={this.handleSubmit}
            >
              Submit
            </Button>
          </form>
        </div>
      </Container>
    );
  }
}

export default withStyles(styles, { withTheme: true })(AddProduct);