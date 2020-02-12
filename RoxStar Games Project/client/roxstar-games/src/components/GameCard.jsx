import React from 'react';
import { withStyles } from "@material-ui/core/styles";
import { Link } from 'react-router-dom';
import Card from "@material-ui/core/Card";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import CardContent from "@material-ui/core/CardContent";
import CardActions from "@material-ui/core/CardActions";
import Typography from "@material-ui/core/Typography";
import { connect } from 'react-redux';

const styles = theme => ({
  card: {
    height: 510,
    maxWidth: 320,
    textAlign: "center",
    padding: 7,
    backgroundColor: "#fcf4e8"
  },
  platform: {
    backgroundColor: "pink"
  },
  media: {
    height: 230
  },
  button: {
    backgroundColor: "salmon",
    color: "black",
    fontWeight: "bold",
    marginLeft: 80,
    boxShadow: "3px 3px darkred"
  },
  priceText: {
    fontWeight: "bold",
    color: "black"
  }
});

class GameCard extends React.Component {
  constructor() {
    super();
    this.state = { product: {},  qty: 0 };
  }

  handleClick = () =>  {
    window.location.href = '/game/' + this.props.game.id;
  }


  render() {
    const { classes } = this.props;
    return (
      <React.Fragment>
        <Card className={classes.card} raised>
          <Link to={'/game/'+this.props.game.id}>
            <img src={"/images/" + this.props.game.imageUrl} className={classes.media} alt="product" />
            <CardHeader title={this.props.game.title} style={{ backgroundColor: "salmon", 
                                                                            border: "2px solid darkred", height: 50}} />
            <Typography className={classes.platform} variant="h6">{this.props.game.platform}</Typography>
          </Link>
          <CardContent>
            {this.props.game.onSale ? 
            <div>
              <Typography className={classes.priceText} style={{textDecoration : "line-through", display : "inline"}} variant="h6">${this.props.game.price}</Typography>
              <Typography className={classes.priceText} variant="h6">${this.props.game.price * 0.8}</Typography>
            </div> :
            <div>
              <Typography className={classes.priceText} variant="h6">${this.props.game.price}</Typography>
              <Typography style={{color : "lightyellow"}} variant="h6">.</Typography>
            </div>
            }
          </CardContent>
          <CardActions disableSpacing>
            <Button className={classes.button} onClick={this.handleClick}>View game</Button>
          </CardActions>
        </Card>
      </React.Fragment>
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

export default connect(mapStateToProps, mapDispatchToProps) (withStyles(styles, { withTheme: true })(GameCard));