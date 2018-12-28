import React, { Component } from 'react';

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    const response = fetch('http://localhost:8080/lol');
    console.log(response.json);
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

   handleSubmit(event) {
      event.preventDefault();
      const Http = new XMLHttpRequest();
      const url = 'http://localhost:8080/lol';
      Http.open("GET", url);
      Http.send();
      Http.onreadystatechange = (e) => {
        console.log(Http.responseText);
      }
    }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <div className="row">
          <label> Courses </label>
          <input type="text" value={this.state.value} onChange={this.handleChange} />
        </div>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

export default Form;