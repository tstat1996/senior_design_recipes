import React, { Component } from 'react';
import '../css/App.css';
import CosineForm from './CosineForm';
import SVDForm from './SVDForm';

import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showSVDForm : false,
    };

    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    const value = event.target.value;
    this.setState({showSVDForm: value == "svd"});
  }

  render() {
    const { showSVDForm } = this.state;
    return (
      <div className="homepage">
        <p>
          Welcome to Penn Course Recs!
        </p>
        <FormControl>
          <label>Please select a recommendation algorithm:</label>
          <RadioGroup
            value={this.state.showSVDForm ? "svd" : "cosine"}
            onChange={this.handleChange}
          >
            <FormControlLabel value="cosine" control={<Radio />} label="Cosine Similarity" />
            <FormControlLabel value="svd" control={<Radio />} label="SVD Matrix" />
          </RadioGroup>
        </FormControl>
        {showSVDForm ? <SVDForm /> : <CosineForm />}
      </div>
    );
  }
}

export default App;
