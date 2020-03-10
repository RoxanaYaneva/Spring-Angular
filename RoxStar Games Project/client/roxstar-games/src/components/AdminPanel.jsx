import React from 'react';
import { withStyles } from "@material-ui/core/styles";
import { Grid, Typography } from "@material-ui/core";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import sendRequest from '../Request';

const styles = theme => ({
  text: {
    fontWeight: "bold"
  },
  header: {
    backgroundColor: "salmon"
  },
  headerTitle: {
    fontWeight: "bold",
    fontSize: 20
  },
  button: {
    color: "white",
    fontWeight: "bold",
    margin: 120,
    boxShadow: "3px 3px darkred"
  }
});

class AdminPanel extends React.Component {
  constructor() {
    super();
    this.state = { users: [] };
  }

  componentDidMount() {
    const uri = '/api/users'
    sendRequest(uri, 'GET', {}, (response) => {
      this.setState({ users: response });
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <Grid style={{ marginBottom: 150 }} container spacing={3} justify="center" className={classes.root}>
        <Grid item xs={8}>
          <Typography className={classes.text} variant="h4">
            All users
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <Table className={classes.table} aria-label="customized table">
            <TableHead>
              <TableRow className={classes.header}>
                <TableCell className={classes.headerTitle}>Email</TableCell>
                <TableCell align="right" className={classes.headerTitle}></TableCell>
                <TableCell align="right" className={classes.headerTitle}>First name</TableCell>
                <TableCell align="right" className={classes.headerTitle}>Last name</TableCell>
                <TableCell align="right" className={classes.headerTitle}>Created</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {this.state.users.map(user => (
                <TableRow key={user.id}>
                  <TableCell component="th" scope="row">
                    {user.email}
                  </TableCell>
                  <TableCell align="right"></TableCell>
                  <TableCell align="right">{user.firstName}</TableCell>
                  <TableCell align="right">{user.lastName}</TableCell>
                  <TableCell align="right">{user.updated}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Grid>
      </Grid>
    );
  }

}

export default withStyles(styles, { withTheme: true })(AdminPanel);

