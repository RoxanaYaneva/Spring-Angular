import React from 'react';
import { Grid, Typography, Divider, Button, InputBase } from '@material-ui/core';
import { withStyles } from "@material-ui/core/styles";
import CommentSection from './CommentSection';
import sendRequest from '../Request';
import { connect } from 'react-redux';

const styles = theme => ({
  error: {
    height: 400,
    marginLeft: 330
  },
  media: {
    height: 300,
    maxWidth: 400
  },
  text: {
    fontWeight: "bold",
    marginBottom: 20
  },
  text1: {
    fontWeight: "bold"
  },
  button: {
    backgroundColor: "salmon",
    color: "black",
    fontWeight: "bold",
    marginLeft: 80,
    boxShadow: "3px 3px darkred"
  },
  input: {
    border: "1px solid black",
    width: 120,
    margin: 20,
    borderRadius: 10,
    backgroundColor: "white",
    padding: "0 10px"
  },
  textarea: {
    width: "100%",
    height: 100,
    border: "1px solid darkred"
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
    borderRadius: 5,
    border: "none",
    padding: 5,
    fontSize: 18,
    backgroundColor: "salmon",
    color: "black",
    fontWeight: "bold",
    boxShadow: "3px 3px darkred"
  }
});

class GameDetail extends React.Component {
  constructor() {
    super();
    this.state = { product: {} , comments: [], text: '', qty: 0 };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  componentDidMount() {
    var id = document.URL.substring(document.URL.lastIndexOf('/') + 1);
    const uri = '/api/products/' + id;
    sendRequest(uri, 'GET', {}, (response) => {
      this.setState({ product: response });
    });
    sendRequest(uri + '/comments', 'GET', {}, (response) => {
      this.setState({ comments: response });
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

  handleSubmit = (event) => {
    // event.preventDefault();
    const url = `/api/products/${this.state.product.id}/comments`;
    var usr = {id : 5, email : 'roxy@gmail.com'};
    var comment = {user: usr, product : this.state.product, text : this.state.text}
    sendRequest(url, 'POST', JSON.stringify(comment) , (response) => {
        // notify.show('Коментарът Ви беше добавен успешно!', 'success', 1500);
    
    });
    const url2 = `/api/products/${this.state.product.id}/comments`;
        this.setState({ text : '' });
        sendRequest(url2, 'GET', {}, (response) => {
            this.setState({comments: response});
        });
  }

  handleClick = () =>  {
    var prodQty = this.state.product;
    prodQty.qty = this.state.qty;
    this.setState( {product : prodQty });
    this.props.addProductToCart(this.state.product);
    // notify.show('Продуктът беше добавен в количката Ви!','success', 2000,);
    this.props.history.push('/cart');
  }

  render() {
    const { classes } = this.props;
    return (
      this.state.product.id === undefined ?
      <div>
        <Typography className={classes.error} variant="h4">There is no game with this name!</Typography>
      </div>
      :
      <div className={classes.root}>
        <Grid container spacing={2} justify="center">
          <Grid item xs={3}>
            <img src={"/images/" + this.state.product.imageUrl} className={classes.media} alt="iamge" />
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h3" className={classes.text}>
              {this.state.product.title}
            </Typography>
            <Typography variant="h5" className={classes.text}>
              Platform: {this.state.product.platform}
            </Typography>
            <Typography variant="h6" className={classes.text}>
              Price: ${this.state.product.price}
            </Typography>
            <Typography variant="h6" className={classes.text}>
              Genre: {this.state.product.genre}
            </Typography>
            <Typography variant="h6" className={classes.text}>
              Type: {this.state.product.type}
            </Typography>
          </Grid>
          <Grid item xs={2}>
            <Grid container justify="center">
              <Grid item xs={12} variant="h5" className={classes.text}>
                Quantity:
                <InputBase onChange={e => this.setState({qty : e.target.value})} className={classes.input} />
              </Grid>
              <Grid item xs={11}>
                <Button className={classes.button} onClick={this.handleClick}>Add to Cart</Button>
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={8}>
            <Typography variant="h5" className={classes.text1}>
              Desctiption
            </Typography>
            <Divider />
            <Typography component="paragraph">
              <div style={{ padding: "20px 0", fontSize: 20, textAlign: "justify" }}>
                {this.state.product.description}
              </div>
            </Typography>
          </Grid>
          <Grid item xs={8}>
            <Typography variant="h5" className={classes.text1}>
              Comments
            </Typography>
            <Divider />
            {this.props.userId &&
            <Grid>
              <br />
              <Typography>{this.props.userId}</Typography>
              <textarea className={classes.textarea} onChange={e => this.setState({text : e.target.value})}
                        defaultValue={this.state.text} placeholder="Maximum 240 characters..."></textarea>
              <input className={classes.submit} onClick={this.handleSubmit} type="submit" name="submit" value="Submit" />  
            </Grid>
            } 
            {this.state.comments && this.state.comments.lenght > 0 ? 
            <CommentSection comments={this.state.comments} />
            :
            <Typography variant="h6">No comments yet!</Typography>
            }
          </Grid>
        </Grid>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
      userId: state.userId
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
      addProductToCart: (product) => {
        dispatch({type: 'ADD_PRODUCT_TO_CART', product});
      } 
  }
}

export default connect(mapStateToProps, mapDispatchToProps) (withStyles(styles, { withTheme: true })(GameDetail));