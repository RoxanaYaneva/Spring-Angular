import React from 'react';
import { NavLink } from 'react-router-dom';
import { withStyles } from "@material-ui/core/styles";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import styles from "./FormStyle.jsx";
import sendRequest from "../Request.js";
import { notify } from 'react-notify-toast';
import { connect } from 'react-redux';

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = { email: '', password: '', userId: '', token: '' };

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

  handleClick = (event) => {
    event.preventDefault();
    this.props.history.push('/register');
  }

  handleSubmit(event) {
    event.preventDefault();
    const url = '/api/auth/login';
    var body =  { email: this.state.email, password: this.state.password };
    sendRequest(url, 'POST', JSON.stringify(body), (response) => {
      if (response.accessToken !== undefined) {
        this.props.setUserIdAndToken(this.state.email, response.accessToken);
        notify.show('You logged in successfully', 'success', 1500);
        this.props.history.push('/');
      }
    });
  }

  render() {
    const { classes } = this.props;
    if (this.props.userId) {
      return (
          <div className={classes.paper}>
              <h1>You are already logged in!</h1>
          </div>
      );
    } else {
      return (
        <Container component="main" maxWidth="xs" className={classes.root}>
          <CssBaseline />
          <div className={classes.paper}>
            <Avatar className={classes.avatar}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Log in
            </Typography>
            <form className={classes.form} noValidate onSubmit={this.handleSubmit}>
              <TextField
                className={classes.input}
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
                value={this.state.email}
                onChange={this.handleInputChange}
              />
              <TextField
                className={classes.input}
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                value={this.state.password}
                onChange={this.handleInputChange}
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                className={classes.submit}
              >
                Log in
              </Button>
              <Grid container>
                <Grid item xs>
                  <NavLink className="menu__link" to="/login" variant="body2">Forgot password?</NavLink>
                </Grid>
                <Grid item>
                  <NavLink className="menu__link" to="/register" variant="body2">Don't have an account? Register.</NavLink>
                </Grid>
              </Grid>
            </form>
          </div>
        </Container>
      );
    }
  }
}

const mapStateToProps = (state) => {
  return {
      userId: state.userId,
      toke: state.token
  }
}

const mapDispatchToProps = dispatch => {
  return {
      setUserIdAndToken: (userId, token) => {
          dispatch({type: 'LOGIN', userId, token})
      }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles, { withTheme: true }) (Login)) ;