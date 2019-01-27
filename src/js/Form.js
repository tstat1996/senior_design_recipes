import React, { Component } from 'react';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import MenuItem from '@material-ui/core/MenuItem';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import '../css/Form.css';

const styles = theme => ({
  button: {
    fontSize: '14pt',
    marginTop: '20px',
    textTransform: 'none',
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
});

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {
        courses: 'CIS-197',
        courseHistory: 'CIS-110 CIS-120 CIS-160',
        difficulty: '3',
        mounted: false,
        quality: '3',
        recs: [],
    };

    this.handleCourseChange = this.handleCourseChange.bind(this);
    this.handleCourseHistoryChange = this.handleCourseHistoryChange.bind(this);
    this.handleDifficultyChange = this.handleDifficultyChange.bind(this);
    this.handleQualityChange = this.handleQualityChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.renderRecs = this.renderRecs.bind(this);
  }

  componentDidMount() {
    this.setState({ mounted: true });
  }

  handleCourseChange(event) {
    this.setState({courses: event.target.value});
  }

  handleCourseHistoryChange(event) {
    this.setState({courseHistory: event.target.value});
  }

  handleDifficultyChange(event) {
    this.setState({difficulty: event.target.value});
  }

  handleQualityChange(event) {
    this.setState({quality: event.target.value});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { courses, difficulty, quality } = this.state;
    const url = `http://localhost:8080/request/?courses=${courses}&diff=${difficulty}&courseQual=${quality}`;
    const response = await fetch(url);
      response.json()
      .then(data => {
        this.setState({ recs: data });
      });
  }

  renderRecs() {
    const { recs } = this.state;
    if (!recs.length) {
        return null;
    }
    return (
      <div className="table">
        <p> Here are some courses you might enjoy! </p>
        <table>
          <tr>
            <th>Course Code</th>
            <th>Course Name</th>
            <th>Course Quality</th>
            <th>Course Difficulty</th>
            <th>Professor Quality</th>
            <th>Course Description</th>
          </tr>
            {recs.map(rec =>
              <tr>
                <td>{rec.code}</td>
                <td>{rec.name}</td>
                <td>{rec.courseQuality}</td>
                <td>{rec.courseDifficulty}</td>
                <td>{rec.profQuality}</td>
                <td>{rec.description}</td>
              </tr>
            )}
        </table>
      </div>
    );
  }

  render() {
    const { mounted } = this.state;
    if (!mounted) return null;
    const recs = this.renderRecs();
    const { classes } = this.props;
    return (
        <div className={classes.container}>
          <form onSubmit={this.handleSubmit}>
            <p className="info"> Please enter your courses in the following format: CIS-197 ACCT-101 </p>
            <div className="row">
              <label> Courses You Liked </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.courses}
                onChange={this.handleCourseChange}
              />
            </div>
            <div className="row">
              <label> Courses You Took </label>
              <TextField
                className={classes.textField}
                multiline
                type="text"
                variant="outlined"
                value={this.state.courseHistory}
                onChange={this.handleCourseHistoryChange}
              />
            </div>
            <div className="row">
              <label> Ideal Course Difficulty </label>
              <Select
                value={this.state.difficulty}
                onChange={this.handleDifficultyChange}
                input={
                  <OutlinedInput
                    name="difficulty"
                  />
                }
              >
                <MenuItem value={1}>0-1</MenuItem>
                <MenuItem value={2}>1-2</MenuItem>
                <MenuItem value={3}>2-3</MenuItem>
                <MenuItem value={4}>3-4</MenuItem>
              </Select>
            </div>
            <div className="row">
              <label> Ideal Course Quality </label>
              <Select
                value={this.state.difficulty}
                onChange={this.handleQualityChange}
                input={
                  <OutlinedInput
                    name="quality"
                  />
                }
              >
                <MenuItem value={1}>0-1</MenuItem>
                <MenuItem value={2}>1-2</MenuItem>
                <MenuItem value={3}>2-3</MenuItem>
                <MenuItem value={4}>3-4</MenuItem>
              </Select>
            </div>
            <Button
              className={classes.button}
              variant="outlined"
              type="submit"
            >
             Submit
            </Button>
          </form>
          {recs}
        </div>
    );
  }
}

export default withStyles(styles)(Form);