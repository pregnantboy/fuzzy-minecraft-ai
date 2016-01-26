//
//  ToggleTableViewCell.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "ToggleTableViewCell.h"
#import "MainMenuViewController.h"
@interface ToggleTableViewCell (){
    NSInteger type;
    NSInteger currIndex;
    NSArray *operators;
}
@end

@implementation ToggleTableViewCell

- (void)drawRect:(NSRect)dirtyRect {
    [super drawRect:dirtyRect];
}

- (void)setType:(NSInteger)column {
    currIndex = 0;
    switch (column){
        case 0:
            type = 0;
            operators = [[NSArray alloc] initWithObjects:@"IF", nil];
            break;
        case 1:
            type = 1;
            operators = [[NSArray alloc] initWithObjects:@"AND", @"OR", nil];
            break;
        default:
            type = 3;
            operators = [[NSArray alloc] initWithObjects:@"IS", @"IS NOT", nil];
    }
    
    [self updateButtonString];
    
}

- (NSInteger)type {
    return type;
}

- (void)setString:(NSString *)string {
    currIndex = [operators indexOfObject:string];
    [self updateButtonString];
}

- (void) updateButtonString {
    if (type == 0) {
         [MainMenuViewController addWhiteMenuText:self.toggleButton withSize:30 withText:[self getString]];
        NSImage  *newImg = [NSImage imageNamed:@"arrow right"];
        [self.toggleButton setImage:newImg];
    } else {
        [MainMenuViewController addWhiteMenuText:self.toggleButton withSize:22 withText:[self getString]];
        NSImage  *newImg = [NSImage imageNamed:@"oval2"];
        [self.toggleButton setImage:newImg];
    }
}

- (NSString *)getString {
    if (currIndex < [operators count]) {
        return [operators objectAtIndex:currIndex];
    }
    return @"";
}

- (IBAction)onButtonPressed:(id)sender {
    currIndex = [operators indexOfObject:self.toggleButton.title];
    currIndex++;
    currIndex = currIndex % ([operators count]);
    [self updateButtonString];
    
}


@end
