import React, { Component } from 'react';
import '../css/Form.css';

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {
        courses: 'CIS 110',
        difficulty: '3',
        quality: '3',
        recs: [],
    };

    this.handleCourseChange = this.handleCourseChange.bind(this);
    this.handleDifficultyChange = this.handleDifficultyChange.bind(this);
    this.handleQualityChange = this.handleQualityChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.renderRecs = this.renderRecs.bind(this);
  }

  async componentDidMount() {
    const response = await fetch('http://localhost:8080/request');
    response.json()
    .then(data => {
      this.setState({ recs: data });
    });
  }

  handleCourseChange(event) {
    this.setState({courses: event.target.value});
  }

  handleDifficultyChange(event) {
    this.setState({difficulty: event.target.value});
  }

  handleQualityChange(event) {
    this.setState({quality: event.target.value});
  }

   handleSubmit(event) {
      event.preventDefault();
      // get form params
    }

  renderRecs() {
    const { recs } = this.state;
    if (!recs.length) {
        return null;
    }
    return (
        <div>
            <p> Here are some courses you might enjoy! </p>
            {recs.map(rec => <p> {rec.code} : {rec.name} </p>)}
        </div>
    );
  }

  render() {
    const recs = this.renderRecs();
    return (
        <div>
          <form onSubmit={this.handleSubmit}>
            <div className="row">
              <label> Courses </label>
              <input type="text" value={this.state.courses} onChange={this.handleCourseChange} />
            </div>
            <div className="row">
              <label> Difficulty </label>
              <select value={this.state.difficulty} onChange={this.handleDifficultyChange}>
                <option value="1">0-1</option>
                <option value="2">1-2</option>
                <option value="3">2-3</option>
                <option value="4">3-4</option>
              </select>
            </div>
            <div className="row">
              <label> Quality </label>
              <select value={this.state.quality} onChange={this.handleQualityChange}>
                  <option value="1">0-1</option>
                  <option value="2">1-2</option>
                  <option value="3">2-3</option>
                  <option value="4">3-4</option>
                </select>
            </div>
            <input className="button" type="submit" value="Submit" />
          </form>
          {recs}
        </div>
    );
  }
}

export default Form;