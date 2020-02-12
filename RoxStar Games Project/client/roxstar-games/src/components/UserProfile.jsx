import React from 'react';
import { AppBar, Toolbar, Typography, Button, Grid, CssBaseline, Container, TextField, withStyles } from '@material-ui/core';
import GTA from "../assets/images/maleIcon.png";
import { NavLink } from 'react-router-dom';
import sendRequest from '../Request';

const styles = theme => ({
  media: {
    height: 200,
    borderRadius: 200
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
    backgroundColor: "darkred",
    color: "white"
  },
  form: {
    marginTop: 50,
    height: 400
  },
  upload: {
    margin: theme.spacing(3, 0, 2),
    fontSize: 22
  }
});

class UserProfile extends React.Component {
  constructor(props) {
    super(props);

    this.state = { user : { } , password : '' };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  componentDidMount() {
    const uri = `/api/users/5`;
    sendRequest(uri, 'GET', {}, (response) => {
        // notify.show('You registered successfully', 'success', 1500);
        this.setState( {user : response} );
    });
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
    const uri = '/api/users/5';
    var user = { id : 5, firstName : this.state.user.firstName, lastName : this.state.user.lastName, email : this.state.user.email, password : this.state.password };
    sendRequest(uri, 'PUT',
      JSON.stringify(user),
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
      <Grid container spacing={3} justify="center">
          <Grid item xs={3}>
            <img src={GTA} className={classes.media} alt="iamge" />
            <input type='file' id='single' className={classes.upload} onChange={this.onChange} /> 
          </Grid>
          <Grid item xs={3}>
          <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography variant="h4">Update your profile</Typography>
          <form className={classes.form} noValidate onSubmit={this.handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  className={classes.input}
                  autoComplete="fname"
                  name="firstName"
                  variant="outlined"
                  required
                  fullWidth
                  id="firstName"
                  autoFocus
                  value={this.state.user.firstName}
                  onChange={this.handleInputChange.bind(this)}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  id="lastName"
                  
                  name="lastName"
                  autoComplete="lname"
                  value={this.state.user.lastName}
                  onChange={this.handleInputChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  id="email"
                  name="email"
                  value="roxy@gmail.com"
                  readonly
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  className={classes.input}
                  variant="outlined"
                  required
                  fullWidth
                  name="password"
                  type="password"
                  id="password"
                  onChange={ e => this.setState({ password : e.target.value})}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              className={classes.submit}
              onSubmit={this.handleSubmit}
            >
              Change
            </Button>
          </form>
        </div>
      </Container>
          </Grid>
      </Grid>
    );
  }
}

export default withStyles(styles, { withTheme: true })(UserProfile);