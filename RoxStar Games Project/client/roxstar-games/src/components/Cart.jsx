import React from 'react';
import { withStyles } from "@material-ui/core/styles";
import { Grid, Typography, List, Button } from "@material-ui/core";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { NavLink } from 'react-router-dom';
import { connect } from 'react-redux';

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

class Cart extends React.Component {
  constructor() {
    super();
    this.state = { 
        counts: {}
    };
  }

  handleModalInput = (event) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState({
        [name]: value
    });
  }

  handleSelectionChange = (event) => {
    const newCounts = {
        ...this.state.counts,
        [event.target.name] : event.target.value,
    };
    this.setState({ counts: newCounts });
  }

  // orderProducts = () => {
  //   const url =`order`;
  //   const productsToOrder = this.props.products
  //       .map(pr => ({...pr, quantity: this.state.counts[pr.product_name] }));
  //   sendRequest(url, 'PUT', { products: productsToOrder }, (response) => {
  //       this.props.products.forEach(pr => this.props.removeProductFromCart(pr.product_id));
  //       this.forceUpdate();
  //       notify.show(response, 'success', 1500);
  //   });
  // }

  componentWillMount() {
    let newCounts = {};
    this.props.products.forEach(pr => {
        newCounts[pr.product_name] = 1;
    });
    this.setState({ counts: newCounts });
  }

  render() {
    const { classes } = this.props;
    var total = 0;
    // if (this.props.products && this.props.products.lenght !== 0) {
    //   var products = this.props.products;
    //   total = products.reduce((total, product) => total + (product.qty * product.price));
    // }
    return (
      <Grid container spacing={3} justify="center" className={classes.root}>
        <Grid item xs={8}>
          <Typography className={classes.text} variant="h4">
            Shopping Cart
          </Typography>
          <Typography className={classes.text} variant="h6">
            Order detail
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <Table className={classes.table} aria-label="customized table">
            <TableHead>
              <TableRow className={classes.header}>
                <TableCell className={classes.headerTitle}>Title</TableCell>
                <TableCell align="right" className={classes.headerTitle}></TableCell>
                <TableCell align="right" className={classes.headerTitle}>Quantity</TableCell>
                <TableCell align="right" className={classes.headerTitle}>Price</TableCell>
                <TableCell align="right" className={classes.headerTitle}>Total</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {this.props.products.map( product => (
                <TableRow key={product.id}>
                  <TableCell component="th" scope="row">
                    {product.title}
                  </TableCell>
                  <TableCell align="right"></TableCell>
                  <TableCell align="right">{product.qty}</TableCell>
                  <TableCell align="right">{product.price}</TableCell>
                  <TableCell align="right">{product.qty * product.price}</TableCell>
                </TableRow>
              ))}
              <TableRow className={classes.header}>
                <TableCell component="th" scope="row">
                  Total
                  </TableCell>
                <TableCell></TableCell>
                <TableCell></TableCell>
                <TableCell></TableCell>
              <TableCell align="right">{total}</TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </Grid>
        <Grid item xs={8}>
          <List>
            <Button className={classes.button} style={{ backgroundColor: "salmon", color: "white" }}>
              <NavLink to="/" style={{ textDecoration: "none", color: "white" }}>&lt;Continue shopping</NavLink>
            </Button>
            <Button className={classes.button} style={{ backgroundColor: "red" }}>Empty cart</Button>
            <Button className={classes.button} style={{ backgroundColor: "green" }}>Proceed to checkout&gt;</Button>
          </List>
        </Grid>
      </Grid>
    );
  }
}

const mapStateToProps = (state) => {
  return {
      products: state.products
  }
}


const mapDispatchToProps = dispatch => {
  return {
      removeProductFromCart: (id) => {
          dispatch({type: 'REMOVE_PRODUCT_FROM_CART', id })
      }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles, { withTheme: true }) (Cart)) ;