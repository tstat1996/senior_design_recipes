import React, { Component } from 'react';
import '../css/App.css';
import Form from './Form';

class App extends Component {
  render() {
    return (
      <div className="homepage">
        <p>
          Welcome to Penn Course Recs
        </p>
        <Form />
      </div>
    );
  }
}

export default App;
